package com.linkapital.core.services.commission.contract.to.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The commission data.")
@Getter
@Setter
public class CommissionTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The total value.")
    private double total;

    @ApiModelProperty(value = "The disbursement value.")
    private double disbursement;

    @ApiModelProperty(value = "The amortization value.")
    private double amortization;

    @ApiModelProperty(value = "The liquidation value.")
    private double liquidation;

    @ApiModelProperty(value = "The release date.")
    private Date releaseDate;

    @ApiModelProperty(value = "The campaign.")
    private CommissionCampaignTO campaign;

    @ApiModelProperty(value = "The installments list.")
    private List<CommissionInstallmentTO> installments;

    public CommissionTO() {
        this.installments = new ArrayList<>();
    }

    public CommissionTO withCampaign(CommissionCampaignTO campaign) {
        setCampaign(campaign);
        return this;
    }

}
