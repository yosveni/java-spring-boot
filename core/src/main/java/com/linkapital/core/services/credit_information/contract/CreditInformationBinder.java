package com.linkapital.core.services.credit_information.contract;

import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.credit_information.datasource.domain.Earning;
import com.linkapital.core.services.credit_information.datasource.domain.ResumeOperation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static com.linkapital.core.services.credit_information.contract.to.MapTO.ASSUMED_OBLIGATION;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.CNPJ_IF_REQUESTER;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.CODE;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.CONSULT_DATE;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.COUNT_INSTITUTION;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.COUNT_OPERATION;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.COUNT_OPER_DISAGREEMENT;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.COUNT_OPER_SUB_JUICE;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.EARNINGS;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.EXCHANGE_VARIATION;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.MODALITY;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.OPERATIONS;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.PERCENT_DOC_PROCESSED;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.PERCENT_VOL_PROCESSED;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.RESP_TOTAL_DISAGREEMENT;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.RESP_TOTAL_SUB_JUICE;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.START_DATE_RELATIONSHIP;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.VALUE;
import static com.linkapital.core.services.credit_information.contract.to.MapTO.VENDOR_INDIRECT_RISK;
import static com.linkapital.core.util.DateUtil.convert;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface CreditInformationBinder {

    CreditInformationBinder CREDIT_INFORMATION_BINDER = Mappers.getMapper(CreditInformationBinder.class);
    Logger log = getLogger(CreditInformationBinder.class);

    Function<Map<String, Object>, ResumeOperation> buildOperation = map -> {
        var resumeOperation = new ResumeOperation()
                .withModality(map.get(MODALITY) == null
                        ? null
                        : map.get(MODALITY).toString())
                .withExchangeVariation(map.get(EXCHANGE_VARIATION) == null
                        ? null
                        : map.get(EXCHANGE_VARIATION).toString());

        Optional
                .ofNullable((Set<Earning>) map.get(EARNINGS))
                .ifPresent(earnings -> resumeOperation.getEarnings().addAll(earnings));

        return resumeOperation;

    };

    Function<Element, ResumeOperation> parseOperation = element -> {
        var map = new HashMap<String, Object>();
        map.put(MODALITY, element.getElementsByTagName(MODALITY).item(0).getTextContent());
        map.put(EXCHANGE_VARIATION, element.getElementsByTagName(EXCHANGE_VARIATION).item(0).getTextContent());

        return buildOperation.apply(map);
    };

    Function<Map<String, Object>, Earning> buildEarning = map -> new Earning()
            .withCode(map.get(CODE) == null
                    ? null
                    : map.get(CODE).toString())
            .withValue(map.get(VALUE) == null
                    ? 0D
                    : (double) map.get(VALUE));

    Function<Element, Earning> parseEarning = element -> {
        var map = new HashMap<String, Object>();
        map.put(CODE, element.getElementsByTagName(CODE).item(0).getTextContent());
        map.put(VALUE, parseDouble(element.getElementsByTagName(VALUE).item(0).getTextContent()));

        return buildEarning.apply(map);
    };

    Function<Map<String, Object>, CreditInformation> buildCreditInformation = map -> {
        var creditInformation = new CreditInformation();

        creditInformation.setCountOperation(map.get(COUNT_OPERATION) == null
                ? 0
                : parseInt(map.get(COUNT_OPERATION).toString()));
        creditInformation.setCountInstitution(map.get(COUNT_INSTITUTION) == null
                ? 0
                : parseInt(map.get(COUNT_INSTITUTION).toString()));
        creditInformation.setCountOperationSubJudice(map.get(COUNT_OPER_SUB_JUICE) == null
                ? 0
                : parseInt(map.get(COUNT_OPER_SUB_JUICE).toString()));
        creditInformation.setResponsibilityTotalSubJudice(map.get(RESP_TOTAL_SUB_JUICE) == null
                ? 0
                : parseInt(map.get(RESP_TOTAL_SUB_JUICE).toString()));
        creditInformation.setCountOperationDisagreement(map.get(COUNT_OPER_DISAGREEMENT) == null
                ? 0
                : parseInt(map.get(COUNT_OPER_DISAGREEMENT).toString()));
        creditInformation.setResponsibilityTotalDisagreement(map.get(RESP_TOTAL_DISAGREEMENT) == null
                ? 0
                : parseInt(map.get(RESP_TOTAL_DISAGREEMENT).toString()));
        creditInformation.setAssumedObligation(map.get(ASSUMED_OBLIGATION) == null
                ? 0
                : parseDouble(map.get(ASSUMED_OBLIGATION).toString()));
        creditInformation.setVendorIndirectRisk(map.get(VENDOR_INDIRECT_RISK) == null
                ? 0
                : parseDouble(map.get(VENDOR_INDIRECT_RISK).toString()));
        creditInformation.setPercentDocumentProcessed(map.get(PERCENT_DOC_PROCESSED) == null
                ? 0
                : parseDouble(map.get(PERCENT_DOC_PROCESSED).toString()));
        creditInformation.setPercentVolumeProcessed(map.get(PERCENT_VOL_PROCESSED) == null
                ? 0
                : parseDouble(map.get(PERCENT_VOL_PROCESSED).toString()));
        creditInformation.setCnpjIfRequester(map.get(CNPJ_IF_REQUESTER) == null
                ? null
                : map.get(CNPJ_IF_REQUESTER).toString());
        creditInformation.setConsultDate(map.get(CONSULT_DATE) == null
                ? null
                : map.get(CONSULT_DATE).toString());
        creditInformation.setStartRelationshipDate(map.get(START_DATE_RELATIONSHIP) == null
                ? null
                : (Date) (map.get(START_DATE_RELATIONSHIP)));

        Optional
                .ofNullable(map.get(OPERATIONS))
                .ifPresent(operations -> creditInformation.getOperations().addAll((List<ResumeOperation>) operations));

        return creditInformation;
    };

    Function<Element, Map<String, Object>> bindXML = element -> {
        var map = new HashMap<String, Object>();

        map.put(PERCENT_DOC_PROCESSED, parseDouble(element.getElementsByTagName(PERCENT_DOC_PROCESSED).item(0)
                .getTextContent()));
        map.put(PERCENT_VOL_PROCESSED, parseDouble(element.getElementsByTagName(PERCENT_VOL_PROCESSED).item(0)
                .getTextContent()));
        map.put(COUNT_OPERATION, parseInt(element.getElementsByTagName(COUNT_OPERATION).item(0)
                .getTextContent()));
        map.put(COUNT_INSTITUTION, parseInt(element.getElementsByTagName(COUNT_INSTITUTION).item(0)
                .getTextContent()));
        map.put(COUNT_OPER_SUB_JUICE, parseInt(element.getElementsByTagName(COUNT_OPER_SUB_JUICE).item(0)
                .getTextContent()));
        map.put(RESP_TOTAL_SUB_JUICE, parseInt(element.getElementsByTagName(RESP_TOTAL_SUB_JUICE).item(0)
                .getTextContent()));
        map.put(COUNT_OPER_DISAGREEMENT, parseInt(element.getElementsByTagName(COUNT_OPER_DISAGREEMENT).item(0)
                .getTextContent()));
        map.put(RESP_TOTAL_DISAGREEMENT, parseInt(element.getElementsByTagName(RESP_TOTAL_DISAGREEMENT).item(0)
                .getTextContent()));
        map.put(ASSUMED_OBLIGATION, parseDouble(element.getElementsByTagName(ASSUMED_OBLIGATION).item(0)
                .getTextContent()));
        map.put(VENDOR_INDIRECT_RISK, parseDouble(element.getElementsByTagName(VENDOR_INDIRECT_RISK).item(0)
                .getTextContent()));
        map.put(CNPJ_IF_REQUESTER, element.getElementsByTagName(CNPJ_IF_REQUESTER).item(0)
                .getTextContent());
        map.put(CONSULT_DATE, element.getElementsByTagName(CONSULT_DATE).item(0)
                .getTextContent());

        try {
            map.put(START_DATE_RELATIONSHIP, convert(element.getElementsByTagName(START_DATE_RELATIONSHIP).item(0)
                    .getTextContent()));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return map;
    };

    List<CreditInformationTO> bind(List<CreditInformation> source);

}
