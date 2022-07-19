package com.linkapital.core.services.commission.contract.to.get;

import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignLimitTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@ApiModel(description = "The commission campaign limit data.")
@Getter
@Setter
public class CommissionCampaignLimitTO extends CreateCommissionCampaignLimitTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

}
