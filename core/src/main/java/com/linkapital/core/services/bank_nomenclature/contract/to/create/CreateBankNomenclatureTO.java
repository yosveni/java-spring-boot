package com.linkapital.core.services.bank_nomenclature.contract.to.create;

import com.linkapital.core.services.bank_nomenclature.contract.to.BaseBankNomenclatureTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to create the bank nomenclature.")
@Getter
@Setter
public class CreateBankNomenclatureTO extends BaseBankNomenclatureTO {

    @ApiModelProperty(value = "The partner bank list.")
    private List<Long> partnersBank;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.", required = true)
    @NotEmpty
    private List<@Valid CreateBankNomenclatureUrgencyTO> nomenclatureUrgencies;

    public CreateBankNomenclatureTO() {
        this.partnersBank = new ArrayList<>();
    }

}
