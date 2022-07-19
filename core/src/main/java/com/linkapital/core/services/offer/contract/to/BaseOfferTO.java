package com.linkapital.core.services.offer.contract.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseOfferTO implements Serializable {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The next step description.")
    private String nextStepDescription;

    @ApiModelProperty(value = "The rejected reason.")
    private String rejectedReason;

    @ApiModelProperty(value = "The month cet.")
    private int monthCet;

    @ApiModelProperty(value = "The year cet.")
    private int yearCet;

    @ApiModelProperty(value = "The type.", required = true)
    @Min(1)
    @Max(4)
    @NotNull
    private int type;

    @ApiModelProperty(value = "The volume.")
    private double volume;

    @ApiModelProperty(value = "The installments.")
    private double installments;

    @ApiModelProperty(value = "The tax percent.")
    private double taxPercent;

    @ApiModelProperty(value = "The tax value.")
    private double taxValue;

    @ApiModelProperty(value = "The discount.")
    private double discount;

    @ApiModelProperty(value = "The iof.")
    private double iof;

    @ApiModelProperty(value = "The registration fee.")
    private double registrationFee;

    @ApiModelProperty(value = "The total.")
    private double total;

    @ApiModelProperty(value = "The pay by installment.")
    private double payByInstallment;

    @ApiModelProperty(value = "The response time.")
    private Date responseTime;

}
