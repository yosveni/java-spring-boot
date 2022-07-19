package com.linkapital.core.services.invoice.contract;

import com.linkapital.core.services.invoice.contract.enums.InvoiceType;
import com.linkapital.core.services.invoice.datasource.domain.IndirectTax;
import com.linkapital.core.services.invoice.datasource.domain.Invoice;
import com.linkapital.core.services.invoice.datasource.domain.Product;
import com.linkapital.core.services.invoice.datasource.domain.SenderRecipient;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

import static com.linkapital.core.services.invoice.contract.enums.InvoiceStatus.UNKNOWN;
import static com.linkapital.core.services.invoice.contract.enums.InvoiceType.ISSUED;
import static com.linkapital.core.services.invoice.contract.enums.InvoiceType.RECEIVED;
import static com.linkapital.core.services.invoice.datasource.domain.CfopData.codeCfopMap;
import static com.linkapital.core.util.DateUtil.convert;
import static com.linkapital.core.util.DateUtil.yearsDiff;
import static com.linkapital.core.util.ParseUtil.encodeString;
import static java.lang.Boolean.TRUE;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.math.RoundingMode.HALF_UP;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface InvoiceBinder {

    Logger log = getLogger(InvoiceBinder.class);

    ToDoubleFunction<String> toDouble = s -> hasText(s)
            ? BigDecimal
            .valueOf(parseDouble(s))
            .setScale(3, HALF_UP)
            .doubleValue()
            : 0D;

    ToLongFunction<String> toLong = s -> hasText(s) ? Long.parseLong(s) : 0L;

    Function<NodeList, String> nodeValue = nodeList -> Optional
            .ofNullable(nodeList.item(0))
            .flatMap(node -> Optional.ofNullable(node.getChildNodes().item(0)))
            .flatMap(node -> Optional.ofNullable(node.getNodeValue()))
            .orElse(null);

    Function<Element, String> getDueDate = element -> {
        var dueDate = nodeValue.apply(element.getElementsByTagName("dVenc"));
        return hasText(dueDate)
                ? dueDate
                : nodeValue.apply(element.getElementsByTagName("dhEmi"));
    };

    Function<NodeList, Optional<String>> getCode = nodeList -> Optional
            .ofNullable(nodeList.item(0))
            .flatMap(node -> Optional.ofNullable(node.getAttributes().getNamedItem("Id")))
            .flatMap(node -> Optional.ofNullable(node.getNodeValue()));

    BiFunction<Boolean, Element, SenderRecipient> buildSenderRecipient = (isEmit, element) -> {
        var senderRecipient = new SenderRecipient();
        senderRecipient.setCnpj(nodeValue.apply(element.getElementsByTagName("CNPJ")));
        senderRecipient.setCnae(nodeValue.apply(element.getElementsByTagName("CNAE")));
        senderRecipient.setName(encodeString.apply(nodeValue.apply(element.getElementsByTagName("xNome")),
                ISO_8859_1));
        senderRecipient.setEmail(nodeValue.apply(element.getElementsByTagName("email")));
        senderRecipient.setFantasyName(encodeString.apply(nodeValue.apply(element.getElementsByTagName("xFant")),
                ISO_8859_1));
        senderRecipient.setIe(nodeValue.apply(element.getElementsByTagName("IE")));
        senderRecipient.setIm(nodeValue.apply(element.getElementsByTagName("IM")));
        senderRecipient.setCrt(nodeValue.apply(element.getElementsByTagName("CRT")));

        var addressNode = element.getElementsByTagName(TRUE.equals(isEmit)
                ? "enderDest"
                : "enderEmit");

        if (addressNode.getLength() > 0) {
            var node2 = addressNode.item(0);

            if (node2 instanceof Element element2) {
                var address = new Address();
                address.setNumber(nodeValue.apply(element2.getElementsByTagName("nro")));
                address.setAddress1(nodeValue.apply(element2.getElementsByTagName("xLgr")));
                address.setNeighborhood(nodeValue.apply(element2.getElementsByTagName("xBairro")));
                address.setMunicipality(nodeValue.apply(element2.getElementsByTagName("xMun")));
                address.setCodeMunicipality(nodeValue.apply(element2.getElementsByTagName("cMun")));
                address.setUf(nodeValue.apply(element2.getElementsByTagName("UF")));
                address.setZip(nodeValue.apply(element2.getElementsByTagName("CEP")));
                address.setCodeCountry(nodeValue.apply(element2.getElementsByTagName("cPais")));
                address.setCountry(nodeValue.apply(element2.getElementsByTagName("xPais")));

                var phone = nodeValue.apply(element2.getElementsByTagName("fone"));
                if (hasText(phone))
                    address.getRfPhones().add(phone);

                senderRecipient.setAddress(address);
            }
        }

        return senderRecipient;
    };

    Function<Element, Product> buildProduct = element -> {
        var product = new Product();
        product.setCode(nodeValue.apply(element.getElementsByTagName("cProd")));
        product.setDescription(encodeString.apply(nodeValue.apply(element.getElementsByTagName("xProd")), ISO_8859_1));
        product.setNcm(nodeValue.apply(element.getElementsByTagName("NCM")));
        product.setCount(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("qCom"))));
        product.setPrice(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vUnTrib"))));
        product.setCfop(toLong.applyAsLong(nodeValue.apply(element.getElementsByTagName("CFOP"))));
        product.setCEan(nodeValue.apply(element.getElementsByTagName("cEAN")));
        product.setCest(nodeValue.apply(element.getElementsByTagName("CEST")));
        product.setUCom(nodeValue.apply(element.getElementsByTagName("uCom")));
        product.setVUnCom(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vUnCom"))));
        product.setVProd(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vProd"))));
        product.setCEanTrib(nodeValue.apply(element.getElementsByTagName("cEANTrib")));
        product.setUTrib(nodeValue.apply(element.getElementsByTagName("uTrib")));
        product.setQTrib(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("qTrib"))));
        product.setIndTot(nodeValue.apply(element.getElementsByTagName("indTot")));
        return product;
    };

    BiFunction<Element, Invoice, Invoice> parseProducts = (element, invoice) -> {
        var nodeList = element.getElementsByTagName("prod");

        if (nodeList.getLength() > 0)
            for (var i = 0; i < nodeList.getLength(); i++) {
                var node = nodeList.item(i);

                if (node instanceof Element prod) {
                    var product = buildProduct.apply(prod);
                    invoice.getProducts().add(product);

                    if (i == 0)
                        invoice.setStatus(codeCfopMap.getOrDefault(product.getCfop(), UNKNOWN));
                }
            }

        return invoice;
    };

    Function<Element, IndirectTax> buildIndirectTax = element -> {
        var indirectTax = new IndirectTax();
        indirectTax.setIpi(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vIPI"))));
        indirectTax.setPis(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vPIS"))));
        indirectTax.setCofins(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vCOFINS"))));
        indirectTax.setIcms(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vICMS"))));
        indirectTax.setVBc(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vBC"))));
        indirectTax.setVIcmsDeson(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vICMSDeson"))));
        indirectTax.setVFcpufDest(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vFCPUFDest"))));
        indirectTax.setVIcmsufDest(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vICMSUFDest"))));
        indirectTax.setVIcmsufRemet(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vICMSUFRemet"))));
        indirectTax.setVFcp(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vFCP"))));
        indirectTax.setVBcst(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vBCST"))));
        indirectTax.setVSt(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vST"))));
        indirectTax.setVFcpst(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vFCPST"))));
        indirectTax.setVFcpstRet(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vFCPSTRet"))));
        indirectTax.setVProd(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vProd"))));
        indirectTax.setVFrete(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vFrete"))));
        indirectTax.setVSeg(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vSeg"))));
        indirectTax.setVDesc(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vDesc"))));
        indirectTax.setV_Ii(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vII"))));
        indirectTax.setVIpiDevol(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vIPIDevol"))));
        indirectTax.setVOutro(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vOutro"))));
        indirectTax.setVNf(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vNF"))));

        return indirectTax;
    };

    Predicate<NodeList> isInvoiceAuthorized = source -> {
        if (source.getLength() > 0) {
            var node = source.item(0);

            if (node instanceof Element element) {
                var nodeList1 = element.getElementsByTagName("nProt");
                if (nodeList1.getLength() == 0 || !hasText(nodeValue.apply(nodeList1)))
                    return false;

                var nodeList2 = element.getElementsByTagName("cStat");

                return nodeList2.getLength() != 0 && parseInt(nodeValue.apply(nodeList2)) == 100;
            }
        }

        return false;
    };

    TriFunction<Element, String, List<Invoice>, Invoice> initParseInvoice = (element, cnpjCompany, list) -> {
        if (!isInvoiceAuthorized.test(element.getElementsByTagName("protNFe")))
            return null;

        Date issuanceDate = null;
        try {
            issuanceDate = convert(nodeValue.apply(element.getElementsByTagName("dhEmi")));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        if (issuanceDate == null || yearsDiff(issuanceDate, new Date()) > 3)
            return null;

        var finalIssuanceDate = issuanceDate;

        return getCode.apply(element.getElementsByTagName("infNFe"))
                .flatMap(code -> {
                    var find = list
                            .stream()
                            .anyMatch(invoice -> invoice.getCode().equals(code));

                    if (!find) {
                        var invoice = parseTypeSenderRecipientAndReceiptRecipient(cnpjCompany, element);
                        if (invoice == null)
                            return Optional.empty();

                        invoice.setCode(code);
                        invoice.setNumber(nodeValue.apply(element.getElementsByTagName("cNF")));
                        invoice.setTotal(toDouble.applyAsDouble(nodeValue.apply(element.getElementsByTagName("vNF"))));
                        invoice.setNatOp(nodeValue.apply(element.getElementsByTagName("natOp")));
                        invoice.setIssuanceDate(finalIssuanceDate);
                        invoice.setIndirectTax(buildIndirectTax.apply(element));
                        invoice.setType(invoice.getType());

                        try {
                            var dueDate = getDueDate.apply(element);
                            if (dueDate != null)
                                invoice.setDueDate(convert(dueDate));

                            var dhEmi = nodeValue.apply(element.getElementsByTagName("dhEmi"));
                            if (dhEmi != null)
                                invoice.setDhEmi(convert(dhEmi));

                            var dhSaiEnt = nodeValue.apply(element.getElementsByTagName("dhSaiEnt"));
                            if (dhSaiEnt != null)
                                invoice.setDhSaiEnt(convert(dhSaiEnt));
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return Optional.of(invoice);
                    }

                    return Optional.empty();
                })
                .orElse(null);
    };

    static Invoice parseTypeSenderRecipientAndReceiptRecipient(String cnpj, Element element) {
        var nodeEmitList = element.getElementsByTagName("emit");
        var nodeDestList = element.getElementsByTagName("dest");

        if (nodeEmitList.getLength() > 0 && nodeDestList.getLength() > 0) {
            var nodeEmit = nodeEmitList.item(0);
            var nodeDest = nodeDestList.item(0);

            if (nodeEmit instanceof Element emitElement && nodeDest instanceof Element destElement) {
                var cnpjEmit = nodeValue.apply(emitElement.getElementsByTagName("CNPJ"));
                var cnpjDest = nodeValue.apply(destElement.getElementsByTagName("CNPJ"));

                var type = decideInvoiceType(cnpj, cnpjEmit, cnpjDest);
                if (type == null)
                    return null;

                return new Invoice()
                        .withType(type)
                        .withSender(buildSenderRecipient.apply(true, (Element) nodeEmit))
                        .withRecipient(buildSenderRecipient.apply(false, (Element) nodeDest));
            }
        }

        return null;
    }

    static InvoiceType decideInvoiceType(String cnpjCompany, String cnpjEmit, String cnpjDest) {
        if (cnpjCompany.equals(cnpjEmit.substring(0, 8)))
            return ISSUED;
        else if (cnpjCompany.equals(cnpjDest.substring(0, 8)))
            return RECEIVED;
        return null;
    }

}