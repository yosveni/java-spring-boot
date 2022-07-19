package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.bank_nomenclature.contract.to.CompanyBankDocumentLightTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.person.contract.to.PersonDocumentTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "All document company.")
@Getter
@Setter
public class DocumentCompanyTO {

    @ApiModelProperty(value = "The sped documents.")
    private DirectoryTO spedDocument;

    @ApiModelProperty(value = "The debt documents.")
    private List<DirectoryTO> debtDocuments;

    @ApiModelProperty(value = "The nfe duplicities.")
    private List<DirectoryTO> nfeDuplicity;

    @ApiModelProperty(value = "The jucesp documents.")
    private List<DirectoryTO> jucespDocuments;

    @ApiModelProperty(value = "The companies banks document.")
    private List<CompanyBankDocumentLightTO> companyBankDocuments;

    @ApiModelProperty(value = "The person documents.")
    private List<PersonDocumentTO> personDocuments;

    public DocumentCompanyTO() {
        this.debtDocuments = new ArrayList<>();
        this.nfeDuplicity = new ArrayList<>();
        this.jucespDocuments = new ArrayList<>();
        this.companyBankDocuments = new ArrayList<>();
        this.personDocuments = new ArrayList<>();
    }

    public DocumentCompanyTO withSpedDocument(DirectoryTO spedDocument) {
        setSpedDocument(spedDocument);
        return this;
    }

    public DocumentCompanyTO withDebtDocuments(List<DirectoryTO> debtDocuments) {
        setDebtDocuments(debtDocuments);
        return this;
    }

    public DocumentCompanyTO withNfeDuplicity(List<DirectoryTO> nfeDuplicity) {
        setNfeDuplicity(nfeDuplicity);
        return this;
    }

    public DocumentCompanyTO withJucespDocuments(List<DirectoryTO> jucespDocuments) {
        setJucespDocuments(jucespDocuments);
        return this;
    }

    public DocumentCompanyTO withCompanyBankDocuments(List<CompanyBankDocumentLightTO> companyBankDocuments) {
        setCompanyBankDocuments(companyBankDocuments);
        return this;
    }

    public DocumentCompanyTO withCompanyPartners(List<PersonDocumentTO> personDocuments) {
        setPersonDocuments(personDocuments);
        return this;
    }

}
