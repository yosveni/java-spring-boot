package com.linkapital.core.services.commission.contract.to.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@ApiModel(description = "The data for create the commission campaign limit.")
@Getter
@Setter
public class CreateCommissionCampaignLimitTO {

    @ApiModelProperty(value = "The amount limit to use this campaign.", required = true)
    @Min(0)
    protected int campaignLimit;

    @ApiModelProperty(value = "The limit amount to use this campaign per user.", required = true)
    @Min(0)
    protected int userLimit;

}
