package com.linkapital.core.services.ibge.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The economic statistics data.")
@Getter
@Setter
public class EconomicStatisticsTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The last year of update of the PIB.")
    private int pibYear;

    @ApiModelProperty(value = "The last year of update of the IDHM.")
    private int idhmYear;

    @ApiModelProperty(value = "The last year of update of the total of the revenue.")
    private int totalRevenueYear;

    @ApiModelProperty(value = "The last year of update of the total of the expenses.")
    private int totalExpensesYear;

    @ApiModelProperty(value = "The last year of update of the percentage revenue sources.")
    private int percentageRevenueSourcesYear;

    @ApiModelProperty(value = "The PIB.")
    private double pib;

    @ApiModelProperty(value = "The Municipal Human Development Index (IDHM).")
    private double idhm;

    @ApiModelProperty(value = "The total revenue.")
    private double totalRevenue;

    @ApiModelProperty(value = "The total expenses.")
    private double totalExpenses;

    @ApiModelProperty(value = "The percentage of the proceeds from external sources.")
    private double percentageRevenueSources;

}
