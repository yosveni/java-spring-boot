package com.linkapital.core.services.credit_information.impl;

import com.linkapital.core.configuration.variable.CreditInformationConfig;
import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.credit_information.CreditInformationService;
import com.linkapital.core.services.credit_information.datasource.CreditInformationRepository;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.credit_information.datasource.domain.Earning;
import com.linkapital.core.services.credit_information.datasource.domain.ResumeOperation;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.NonNull;
import org.apache.http.ParseException;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.bindXML;
import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.buildCreditInformation;
import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.parseEarning;
import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.parseOperation;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.OPERATIONS;
import static java.io.File.createTempFile;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION;
import static org.eclipse.jetty.util.StringUtil.valueOf;
import static org.springframework.http.HttpHeaders.writableHttpHeaders;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Service
public class CreditInformationServiceImpl implements CreditInformationService {

    private static final String CREDIT_ENDPOINT = "/fiducia/srcwebservice";
    private static final String CONSULT_DATE_FORMAT = "yyyy-MM";
    private final DateTimeFormatter dateTimeFormatter;
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final CreditInformationRepository creditInformationRepository;
    private final CreditInformationConfig config;
    private String cnpj;
    private String period;
    //Only for test
    private boolean firstCall;
    private LocalDate date;
    private List<String> consultDates;

    public CreditInformationServiceImpl(RestTemplate restTemplate,
                                        ResourceLoader resourceLoader,
                                        CreditInformationRepository creditInformationRepository,
                                        CreditInformationConfig config) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.creditInformationRepository = creditInformationRepository;
        this.config = config;
        this.period = null;
        this.firstCall = true;
        this.dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(CONSULT_DATE_FORMAT)
                .parseDefaulting(DAY_OF_MONTH, 1)
                .toFormatter()
                .withLocale(ENGLISH);
    }

    @Override
    public List<CreditInformation> getCnpjCreditInformationData(String cnpj) throws CnpjNotFoundException {
        if (config.getDataSource().equals("API")) {
            this.cnpj = cnpj;
            consultDates = creditInformationRepository.getConsultDatesByCompanyCnpj(cnpj);

            try {
                if (consultDates.isEmpty()) {
                    period = now().minusMonths(1).format(ofPattern(CONSULT_DATE_FORMAT));
                    return singletonList(requestCreditInformation());
                }

                return update();
            } catch (Exception e) {
                throw new CnpjNotFoundException(e.getMessage());
            }
        }

        try {
            return singletonList(buildCreditInformation(format("classpath:mock_src/%s.xml", cnpj.substring(0, 8)),
                    false));
        } catch (ParseException | UnprocessableEntityException e) {
            throw new CnpjNotFoundException(e.getMessage());
        }
    }

    //region Builds the SRC API Body recursively updating desired period quarterly
    private @NonNull List<CreditInformation> update() throws UnprocessableEntityException {
        var currentDate = now();
        date = currentDate.minusMonths(1);

        var currentYear = currentDate.getYear();
        var currentMonth = currentDate.getMonth().getValue();
        var creditInformationList = new ArrayList<CreditInformation>();

        switch (currentMonth) {
            case 1 -> creditInformationList.addAll(searchInYear(currentYear - 1, false));
            case 2, 3 -> {
                creditInformationList.addAll(searchInYear(currentYear, true));

                if (date.getYear() == currentYear)
                    date = date.minusYears(1).withMonth(12);

                creditInformationList.addAll(searchInYear(currentYear - 1, false));
            }
            default -> creditInformationList.addAll(searchInYear(currentYear, false));
        }

        var last = searchAndUpdateList(date.withMonth(12));
        if (last != null)
            creditInformationList.add(last);

        var previousLast = searchAndUpdateList(date.minusYears(1).withMonth(12));
        if (previousLast != null)
            creditInformationList.add(previousLast);

        return creditInformationList;
    }
    //endregion

    //region Perform the search for credit information month by month or quarterly
    private @NonNull List<CreditInformation> searchInYear(int year,
                                                          boolean monthlySearch)
            throws UnprocessableEntityException {

        var list = new ArrayList<CreditInformation>();
        if (monthlySearch) {
            var find = false;
            while (!find && date.getYear() >= year) {
                var creditInformation = searchAndUpdateList(date);
                date = date.minusMonths(1);

                if (creditInformation != null) {
                    list.add(creditInformation);
                    find = true;
                }
            }
        } else
            while (date.getYear() >= year) {
                var creditInformation = searchAndUpdateList(date);
                reduceDate();

                if (creditInformation != null)
                    list.add(creditInformation);
            }

        return list;
    }
    //endregion

    //region Reduce the date in analysis by a month or convert it to a quarterly month
    private void reduceDate() {
        var month = date.getMonth().getValue();
        if (month == 1)
            date = date.minusMonths(1);

        date = month % 3 == 0
                ? date.minusMonths(3)
                : date.withMonth(max(1, month / 3) * 3);
    }
    //endregion

    //region Performs the search for credit information given a date, verifying that this date has not been previously
    // searched, if it is found, the list is updated
    private @Nullable CreditInformation searchAndUpdateList(@NonNull LocalDate currentDate)
            throws UnprocessableEntityException {

        period = currentDate.format(dateTimeFormatter);
        if (!consultDates.contains(period)) {
            consultDates.add(period);
            return requestCreditInformation();
        }

        return null;
    }
    //endregion

    @Retry(name = "fiducia")
    private CreditInformation requestCreditInformation() throws UnprocessableEntityException {
        //Only for test. For environments other than PRODUCTION, it simulates the first scr with an error and the rest
        //correct
        if (!config.getDataSource().equals("API")) {
            var data = emptyCreditInformation();
            if (firstCall) {
                firstCall = false;
                return data.withFind(true);
            }

            return data;
        }

        var entity = buildSrcBody(cnpj, period);
        var headers = writableHttpHeaders(entity.getHeaders());
        headers.set("Authorization", "Basic " + config.getToken());
        try {
            var responseEntity = restTemplate
                    .exchange(format("%s/%s", config.getHost(), CREDIT_ENDPOINT),
                            POST, entity, new ParameterizedTypeReference<String>() {
                            });

            return buildCreditInformation(responseEntity.getBody(), true);
        } catch (RestClientException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    //region Builds the SRC API call body
    private @NonNull HttpEntity<String> buildSrcBody(@NonNull String cnpj,
                                                     @NonNull String period) throws UnprocessableEntityException {
        var file = updateXMLFile(cnpj, period);
        return bindHttpEntity(file);
    }
    //endregion

    //region Builds the CreditInformation Object from XML File
    private CreditInformation buildCreditInformation(String xml,
                                                     boolean profileProd)
            throws UnprocessableEntityException {

        try {
            Document document;

            if (profileProd)
                document = newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            else
                document = newInstance().newDocumentBuilder().parse(xml);

            if (document == null)
                return emptyCreditInformation();

            document.getDocumentElement().normalize();

            return parseCreditInformation(document);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
    //endregion

    //region Update the XML File Template that is passed to SRC API
    private @Nullable File updateXMLFile(String cnpj, String period) throws UnprocessableEntityException {
        //---------UPDATE FILE
        var document = getDocumentFromXML();
        if (document == null)
            return null;

        try {
            var element = document.getDocumentElement();
            element.getElementsByTagName("codCliente").item(0).setTextContent(cnpj.substring(0, 8));
            element.getElementsByTagName("tpCliente").item(0).setTextContent(valueOf(config.getClientType()));
            element.getElementsByTagName("dataBase").item(0).setTextContent(period);
            element.getElementsByTagName("autorizacao").item(0).setTextContent("S");

            // --------- OUTPUT
            var file = createTempFile("index", ".xml");
            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(file));

            return file;
        } catch (TransformerException | IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
    //endregion

    //region Return a HttpEntity Object to call the SRC API
    private @NonNull HttpEntity<String> bindHttpEntity(File file) throws UnprocessableEntityException {
        var headers = new HttpHeaders();
        var param = "";

        headers.setContentType(APPLICATION_XML);

        try {
            var document = newInstance().newDocumentBuilder().parse(file);
            var transformer = TransformerFactory.newInstance().newTransformer();
            var writer = new StringWriter();
            //transform document to string
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            param = writer.getBuffer().toString();
        } catch (TransformerException | IOException | ParserConfigurationException | SAXException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return new HttpEntity<>(param, headers);
    }
    //endregion

    //region Parser XML Document
    private CreditInformation parseCreditInformation(@NonNull Document document) {
        var element = document.getDocumentElement();
        if (element.hasAttribute("listaDeMensagensDeValidacao")
                || document.getElementsByTagName("listaDeMensagensDeValidacao").item(0) != null)
            return emptyCreditInformation();

        var map = bindXML.apply(element);
        map.put(OPERATIONS, parseOperations(element));

        return buildCreditInformation.apply(map);
    }
    //endregion

    //region Build the list of credit information operations
    private @NonNull List<ResumeOperation> parseOperations(@NonNull Element element) {
        var operations = new ArrayList<ResumeOperation>();
        var nodeList = element.getElementsByTagName("listaDeResumoDasOperacoes");

        if (nodeList.getLength() > 0) {
            for (var i = 0; i < nodeList.getLength(); i++) {
                var node = nodeList.item(i);
                var childList = nodeList.item(i).getChildNodes();
                var earningList = new HashSet<Earning>();

                for (var j = 0; j < childList.getLength(); j++) {
                    var nodeChild = childList.item(j);
                    var nodeName = nodeChild.getNodeName();

                    if (nodeChild instanceof Element
                            && !"modalidade".equals(nodeName)
                            && !"variacaoCambial".equals(nodeName))
                        earningList.add(parseEarning.apply((Element) nodeChild));
                }

                if (node instanceof Element e) {
                    operations.add(parseOperation.apply(e));
                    operations.get(i).setEarnings(earningList);
                }
            }
        }

        return operations;
    }
    //endregion

    //region Returns a XML file
    private Document getDocumentFromXML() throws UnprocessableEntityException {
        try {
            return newInstance()
                    .newDocumentBuilder()
                    .parse(resourceLoader.getResource("classpath:" + config.getXmlInput()).getInputStream());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
    //endregion

    //region Returns an empty object of credit information, just for testing
    private CreditInformation emptyCreditInformation() {
        return new CreditInformation()
                .withFind(false)
                .withConsultDate(period);
    }
    //endregion

}
