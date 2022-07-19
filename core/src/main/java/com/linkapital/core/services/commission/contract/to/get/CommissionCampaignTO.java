package com.linkapital.core.services.commission.contract.to.get;

import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@ApiModel(description = "The commission campaign data.")
@Getter
@Setter
public class CommissionCampaignTO extends UpdateCommissionCampaignTO {

    @ApiModelProperty(value = "Indicate if it is the base commission.")
    private boolean base;

    public CommissionCampaignTO() {
        super.percents = new ArrayList<>();
        super.conditions = new ArrayList<>();
    }

}
