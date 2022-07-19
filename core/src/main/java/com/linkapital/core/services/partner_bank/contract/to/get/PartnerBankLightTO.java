package com.linkapital.core.services.partner_bank.contract.to.get;

import com.linkapital.core.services.partner_bank.contract.to.BasePartnerBankTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The partner bank data.")
@Getter
@Setter
public class PartnerBankLightTO extends BasePartnerBankTO {

    @ApiModelProperty(value = "The id.", required = true)
    private long id;

}
