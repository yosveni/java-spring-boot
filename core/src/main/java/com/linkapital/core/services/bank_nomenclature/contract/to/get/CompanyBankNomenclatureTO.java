package com.linkapital.core.services.bank_nomenclature.contract.to.get;

import com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState;
import com.linkapital.core.services.bank_nomenclature.contract.to.BaseBankNomenclatureTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data of the bank nomenclature related to the company.")
@Getter
@Setter
public class CompanyBankNomenclatureTO extends BaseBankNomenclatureTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The description state.")
    private String descriptionState;

    @ApiModelProperty(value = "Indicates if the company has documents with this nomenclature.")
    private boolean hasDocument;

    @ApiModelProperty(value = "The state.")
    private CompanyBankDocumentState state;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.")
    private List<BankNomenclatureUrgencyTO> nomenclatureUrgencies;

    public CompanyBankNomenclatureTO() {
        this.nomenclatureUrgencies = new ArrayList<>();
    }

}
