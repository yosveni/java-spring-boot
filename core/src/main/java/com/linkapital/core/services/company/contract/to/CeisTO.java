package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The ceis data.")
@Getter
@Setter
public class CeisTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The organ complement.")
    private String organComplement;

    @ApiModelProperty(value = "The legal substantiation.")
    private String legalSubstantiation;

    @ApiModelProperty(value = "The process number.")
    private String processNumber;

    @ApiModelProperty(value = "The sanctioning entity.")
    private String sanctioningEntity;

    @ApiModelProperty(value = "The UF of sanctioning entity.")
    private String ufSanctioningEntity;

    @ApiModelProperty(value = "The information entity.")
    private String informationEntity;

    @ApiModelProperty(value = "The sanction.")
    private String sanction;

    @ApiModelProperty(value = "The init sanction data.")
    private Date initSanctionDate;

    @ApiModelProperty(value = "The end sanction data.")
    private Date endSanctionDate;

    @ApiModelProperty(value = "The information data.")
    private Date informationDate;

}
