package com.linkapital.core.services.bank_operation.impl;

import com.linkapital.core.services.bank_operation.BankOperationService;
import com.linkapital.core.services.bank_operation.contract.to.BndesResponseTO;
import com.linkapital.core.services.bank_operation.contract.to.ResponseTO;
import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus.OTHER;
import static com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus.getStatus;
import static com.linkapital.core.util.DateUtil.convert;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Service
public class BankOperationServiceImpl implements BankOperationService {

    private static final String uriBase = "https://apigw.bndes.gov.br/operacoes/v1/select?defType=edismax&rows=" +
            "1000&start=0&q=%s";
    private final Logger log = LoggerFactory.getLogger(BankOperationServiceImpl.class);
    private final RestTemplate restTemplate;

    public BankOperationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Retry(name = "bndes")
    public List<BankOperation> getBankOperations(String cnpj) {
        var url = format(uriBase, cnpj);
        try {
            var response = restTemplate.getForEntity(url, ResponseTO.class);
            var body = response.getBody();
            if (response.getStatusCode().value() != 200 || body == null)
                return new ArrayList<>();

            return buildBankOperations(body.getResponse().getDocs());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    private List<BankOperation> buildBankOperations(List<BndesResponseTO> data) {
        return data
                .stream()
                .map(to -> {
                    var uf = to.getUf().length > 0
                            ? to.getUf()[0]
                            : null;
                    var status = to.getLiquidada().length > 0
                            ? getStatus(to.getLiquidada()[0])
                            : OTHER;
                    var product = to.getProdutoBndes().length > 0
                            ? to.getProdutoBndes()[0]
                            : null;
                    var financialCost = to.getCustoFinanceiro().length > 0
                            ? to.getCustoFinanceiro()[0]
                            : null;
                    var financialAgent = to.getAgenteFinanceiro().length > 0
                            ? to.getAgenteFinanceiro()[0]
                            : null;
                    var gracePeriod = to.getPrazoCarencia().length > 0
                            ? parseInt(to.getPrazoCarencia()[0])
                            : 0;
                    var amortizationPeriod = to.getPrazoAmortizacao().length > 0
                            ? parseInt(to.getPrazoAmortizacao()[0])
                            : 0;
                    var tax = to.getTaxaJuros().length > 0
                            ? parseDouble(to.getTaxaJuros()[0])
                            : 0D;

                    Date contractedDate;
                    try {
                        contractedDate = to.getDataContratacao() != null
                                ? convert(to.getDataContratacao())
                                : null;
                    } catch (ParseException e) {
                        contractedDate = null;
                    }

                    return new BankOperation()
                            .withContractedValue(to.getValorContratacao())
                            .withType(to.getTope())
                            .withUf(uf)
                            .withStatus(status)
                            .withProduct(product)
                            .withFinancialCost(financialCost)
                            .withFinancialAgent(financialAgent)
                            .withGracePeriod(gracePeriod)
                            .withAmortizationPeriod(amortizationPeriod)
                            .withTax(tax)
                            .withContractedDate(contractedDate);
                })
                .toList();
    }

}
