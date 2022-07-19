package com.linkapital.core.services.commission.contract.to.update;

import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignConditionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignLimitTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionPaymentPercentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionPercentTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "The commission campaign data.")
@Getter
@Setter
public class UpdateCommissionCampaignTO {

    @ApiModelProperty(value = "The id.", required = true)
    protected long id;

    @ApiModelProperty(value = "The campaign title", required = true)
    protected String title;

    @ApiModelProperty(value = "Indicates if the campaign will be activated", required = true)
    protected boolean active;

    @ApiModelProperty(value = "The data for create the commission campaign limit.")
    @Valid
    protected CommissionCampaignLimitTO limit;

    @ApiModelProperty(value = "The data for create the commission payment percent.", required = true)
    @NotNull
    @Valid
    protected CommissionPaymentPercentTO paymentPercent;

    @ApiModelProperty(value = "The data for create the commission percent.", required = true)
    @NotEmpty
    @Valid
    protected List<@NotNull CommissionPercentTO> percents;

    @ApiModelProperty(value = "The data for create the commission campaign condition.", required = true)
    @NotEmpty
    @Valid
    protected List<@NotNull CommissionCampaignConditionTO> conditions;

}
