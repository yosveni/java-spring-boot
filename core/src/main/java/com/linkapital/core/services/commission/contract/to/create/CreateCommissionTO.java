package com.linkapital.core.services.commission.contract.to.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.util.Date;

@ApiModel(description = "The data needed to build the commission.")
@Getter
@Setter
public class CreateCommissionTO {

    @ApiModelProperty(value = "The offer id.", required = true)
    @Min(1)
    private long offerId;

    @ApiModelProperty(value = "The campaign id.", required = true)
    @Min(1)
    private long campaignId;

    @ApiModelProperty(value = "The release date.", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDate;

}
