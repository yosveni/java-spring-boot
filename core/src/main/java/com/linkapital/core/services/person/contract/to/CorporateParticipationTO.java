package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "The marriage regime data of the person.")
@Getter
@Setter
public class CorporateParticipationTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The cnpj of the company.")
    private String cnpj;

    @ApiModelProperty(value = "The social reason of the company.")
    private String socialReason;

    @ApiModelProperty(value = "The description cnae of the company.")
    private String descriptionCnae;

    @ApiModelProperty(value = "The business activity cnae of the company.")
    private String businessActivityCnae;

    @ApiModelProperty(value = "The social capital of the company.")
    private String socialCapital;

    @ApiModelProperty(value = "The situation of the company.")
    private String situation;

    @ApiModelProperty(value = "The estimated billing of the company.")
    private String estimatedBilling;

    @ApiModelProperty(value = "The estimated billing group of the company.")
    private String estimatedBillingGroup;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The uf.")
    private String uf;

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

    @ApiModelProperty(value = "The opening date of the company.")
    private LocalDateTime openingDate;

}
