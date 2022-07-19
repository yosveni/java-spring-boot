package com.linkapital.core.services.bank_nomenclature.contract.to.update;

import com.linkapital.core.services.bank_nomenclature.contract.to.BaseBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureUrgencyTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The bank nomenclature data to update.")
@Getter
@Setter
public class UpdateBankNomenclatureTO extends BaseBankNomenclatureTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The partner bank list.", required = true)
    private List<Long> partnersBank;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.", required = true)
    @NotEmpty
    private List<@Valid BankNomenclatureUrgencyTO> nomenclatureUrgencies;

    public UpdateBankNomenclatureTO() {
        this.partnersBank = new ArrayList<>();
    }

}
