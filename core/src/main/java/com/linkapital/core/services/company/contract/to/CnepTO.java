package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The cnep data.")
@Getter
@Setter
public class CnepTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The process number.")
    private String processNumber;

    @ApiModelProperty(value = "The sanctioning entity.")
    private String sanctioningEntity;

    @ApiModelProperty(value = "The UF of sanctioning entity.")
    private String ufSanctioningEntity;

    @ApiModelProperty(value = "The sanction.")
    private String sanction;

    @ApiModelProperty(value = "The penalty value.")
    private double penaltyValue;

    @ApiModelProperty(value = "The init sanction data.")
    private Date initSanctionDate;

    @ApiModelProperty(value = "The end sanction data.")
    private Date endSanctionDate;

}
