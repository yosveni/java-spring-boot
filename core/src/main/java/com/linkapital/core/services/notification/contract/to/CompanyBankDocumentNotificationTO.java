package com.linkapital.core.services.notification.contract.to;

import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The company bank document notification data.")
@Getter
@Setter
public class CompanyBankDocumentNotificationTO {

    @ApiModelProperty(value = "The company cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The company bank document.")
    private CompanyBankDocumentTO companyBankDocument;

    public CompanyBankDocumentNotificationTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanyBankDocumentNotificationTO withCompanyBankDocument(CompanyBankDocumentTO companyBankDocument) {
        setCompanyBankDocument(companyBankDocument);
        return this;
    }

}
