package com.linkapital.core.services.commission.contract.to.get;

import com.linkapital.core.services.commission.contract.enums.CampaignAttributeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The commission campaign attribute data.")
@Getter
@Setter
public class CommissionCampaignAttributeTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The name.", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "The attribute type.", required = true)
    @NotNull
    private CampaignAttributeType attributeType;

}
