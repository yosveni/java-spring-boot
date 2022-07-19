package com.linkapital.core.services.partner_bank.contract.to.update;

import com.linkapital.core.services.partner_bank.contract.to.BasePartnerBankTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The partner bank data to update.")
@Getter
@Setter
public class UpdatePartnerBankTO extends BasePartnerBankTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The bank nomenclature list.")
    private List<Long> bankNomenclatures;

    public UpdatePartnerBankTO() {
        this.bankNomenclatures = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName().toUpperCase();
    }

}
