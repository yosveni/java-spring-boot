package com.linkapital.core.services.bank_nomenclature.contract.to.get;

import com.linkapital.core.services.bank_nomenclature.contract.to.BaseBankNomenclatureTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankLightTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The bank nomenclature data.")
@Getter
@Setter
public class BankNomenclatureTO extends BaseBankNomenclatureTO implements Serializable {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The partner bank list.")
    private List<PartnerBankLightTO> partnersBank;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.")
    private List<BankNomenclatureUrgencyTO> nomenclatureUrgencies;

    public BankNomenclatureTO() {
        this.partnersBank = new ArrayList<>();
        this.nomenclatureUrgencies = new ArrayList<>();
    }

}
