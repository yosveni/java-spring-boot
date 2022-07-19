package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Sessions Confirmed.")
@Getter
@Setter
public class SessionConfirmedTO {

    @ApiModelProperty(value = "The company cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "Indicates if the main data of the company is confirmed.")
    private boolean hasDataConfirm;

    @ApiModelProperty(value = "Indicates if the partner data of the company is confirmed.")
    private boolean hasPartnerConfirm;

    @ApiModelProperty(value = "Indicates if the billing data of the company is confirmed.")
    private boolean hasBillingConfirm;

    @ApiModelProperty(value = "Indicates if the functionary data of the company is confirmed.")
    private boolean hasFunctionaryConfirm;

    @ApiModelProperty(value = "Indicates if the relations data of the company is confirmed")
    private boolean hasRelationsConfirm;

    @ApiModelProperty(value = "Indicates if the fiscal data of the company is confirmed.")
    private boolean hasFiscalConfirm;

    @ApiModelProperty(value = "Indicates if the partner data of the company is confirmed.")
    private boolean hasDataPartnerConfirm;

    @ApiModelProperty(value = "Indicates if the espouse data of the company is confirmed.")
    private boolean hasDataSpouseConfirm;

    @ApiModelProperty(value = "Indicates if the property data of the company is confirmed.")
    private boolean hasPropertiesConfirm;

    @ApiModelProperty(value = "Indicates if the horizontal analysis is confirmed.")
    private boolean hasHorizontalAnalysisConfirm;

    @ApiModelProperty(value = "Indicates if the vertical analysis is confirmed.")
    private boolean hasVerticalAnalysisConfirm;

    @ApiModelProperty(value = "Indicates if the cash conversion is confirmed.")
    private boolean hasCashConversionConfirm;

    @ApiModelProperty(value = "Indicates if the cash flow is confirmed.")
    private boolean hasCashFlowConfirm;

    @ApiModelProperty(value = "Indicates if the horizontal and Vertical Analysis is confirmed.")
    private boolean hasHorizontalVerticalAnalysisConfirm;

    @ApiModelProperty(value = "Indicates if the Others analysis is confirmed.")
    private boolean hasOthersAnalysisConfirm;

    @ApiModelProperty(value = "Indicates if the Legal is confirmed.")
    private boolean hasLegalConfirm;

    @ApiModelProperty(value = "Indicates if the sanctions restriction is confirmed.")
    private boolean hasSanctionsRestrictionConfirm;

    @ApiModelProperty(value = "Indicates if the properties company is confirmed.")
    private boolean hasPropertiesCompanyConfirm;

    @ApiModelProperty(value = "Indicates if the cnae is confirmed.")
    private boolean hasCnaeConfirm;

    @ApiModelProperty(value = "Indicates if the corporates data of the company is confirmed.")
    private boolean hasCorporateConfirm;

    @ApiModelProperty(value = "Indicates if the protest data of the company is confirmed.")
    private boolean hasProtest;

    @ApiModelProperty(value = "Indicates if the CRI, CRA and DEBENTURE data of the company is confirmed.")
    private boolean hasCriCraDebenture;

}
