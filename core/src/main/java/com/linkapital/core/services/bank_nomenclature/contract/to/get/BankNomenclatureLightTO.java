package com.linkapital.core.services.bank_nomenclature.contract.to.get;

import com.linkapital.core.services.bank_nomenclature.contract.to.BaseBankNomenclatureTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The bank nomenclature data.")
@Getter
@Setter
public class BankNomenclatureLightTO extends BaseBankNomenclatureTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.")
    private List<BankNomenclatureUrgencyTO> nomenclatureUrgencies;

    public BankNomenclatureLightTO() {
        this.nomenclatureUrgencies = new ArrayList<>();
    }

}
