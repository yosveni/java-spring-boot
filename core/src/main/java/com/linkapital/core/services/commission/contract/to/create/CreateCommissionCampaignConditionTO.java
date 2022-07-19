package com.linkapital.core.services.commission.contract.to.create;

import com.linkapital.core.services.commission.contract.enums.CampaignConnector;
import com.linkapital.core.services.commission.contract.enums.CampaignOperator;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@ApiModel(description = "The data for create the commission campaign condition.")
@Getter
@Setter
public class CreateCommissionCampaignConditionTO {

    @ApiModelProperty(value = "The value of the condition to perform the predicate.", required = true)
    @NotEmpty
    protected String value;

    @ApiModelProperty(value = "The condition operator.", required = true)
    @NotNull
    protected CampaignOperator operator;

    @ApiModelProperty(value = "The condition connector.")
    protected CampaignConnector connector;

    @ApiModelProperty(value = "The attribute that should be used to perform the predicate.", required = true)
    @NotNull
    protected CommissionCampaignAttributeTO campaignAttribute;

}
