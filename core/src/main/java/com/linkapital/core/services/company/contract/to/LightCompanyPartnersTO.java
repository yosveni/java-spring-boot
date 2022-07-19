package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The company partners data (light).")
@Getter
@Setter
public class LightCompanyPartnersTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The qualification.")
    private String qualification;

    @ApiModelProperty(value = "The qualification RF.")
    private String qualificationRF;

    @ApiModelProperty(value = "The level of preparation.")
    private String levelPreparation;

    @ApiModelProperty(value = "The level of preparation RF.")
    private String levelPreparationRF;

    @ApiModelProperty(value = "The participation.")
    private double participation;

    @ApiModelProperty(value = "The participation RF.")
    private double participationRF;

    @ApiModelProperty(value = "The social capital participation.")
    private double participationSocialCapital;

    @ApiModelProperty(value = "The social capital participation RF.")
    private double participationSocialCapitalRF;

    @ApiModelProperty(value = "The entry date.")
    private Date entryDate;

    @ApiModelProperty(value = "The company data.")
    private LightCompanyTO company;

}
