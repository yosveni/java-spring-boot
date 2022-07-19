package com.linkapital.core.services.commission.contract.to.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "The data for create the commission campaign.")
@Getter
@Setter
public class CreateCommissionCampaignTO {

    @ApiModelProperty(value = "The campaign title", required = true)
    @NotEmpty
    protected String title;

    @ApiModelProperty(value = "Indicates if the campaign will be activated", required = true)
    protected boolean active;

    @ApiModelProperty(value = "The data for create the commission campaign limit.")
    protected CreateCommissionCampaignLimitTO limit;

    @ApiModelProperty(value = "The data for create the commission payment percent.", required = true)
    @NotNull
    protected CreateCommissionPaymentPercentTO paymentPercent;

    @ApiModelProperty(value = "The data for create the commission percent.", required = true)
    @NotEmpty
    @Valid
    protected List<@NotNull CreateCommissionPercentTO> percents;

    @ApiModelProperty(value = "The data for create the commission campaign condition.", required = true)
    @NotEmpty
    @Valid
    protected List<@NotNull CreateCommissionCampaignConditionTO> conditions;

}
