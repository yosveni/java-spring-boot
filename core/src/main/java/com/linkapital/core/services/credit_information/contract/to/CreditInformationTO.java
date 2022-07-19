package com.linkapital.core.services.credit_information.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The credit information of the company.")
@Getter
@Setter
public class CreditInformationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cnpj requester.")
    private String cnpjIfRequester;

    @ApiModelProperty(value = "The consultDate in format (yyyy-mm).")
    private String consultDate;

    @ApiModelProperty(value = "The count operation.")
    private int countOperation;

    @ApiModelProperty(value = "The count institution.")
    private int countInstitution;

    @ApiModelProperty(value = "The count Operation SubJudice.")
    private int countOperationSubJudice;

    @ApiModelProperty(value = "The responsibility total subJudice.")
    private int responsibilityTotalSubJudice;

    @ApiModelProperty(value = "The count operation disagreement.")
    private int countOperationDisagreement;

    @ApiModelProperty(value = "The responsibility Total Disagreement.")
    private int responsibilityTotalDisagreement;

    @ApiModelProperty(value = "The assumed obligation.")
    private double assumedObligation;

    @ApiModelProperty(value = "The vendor indirect risk.")
    private double vendorIndirectRisk;

    @ApiModelProperty(value = "The percent document processed.")
    private double percentDocumentProcessed;

    @ApiModelProperty(value = "The percent volume processed.")
    private double percentVolumeProcessed;

    @ApiModelProperty(value = "Indicates if the information was found and does not contain errors.")
    private boolean find;

    @ApiModelProperty(value = "The start relationship date.")
    private Date startRelationshipDate;

    @ApiModelProperty(value = "The created date.")
    private Date created;

    @ApiModelProperty(value = "The list of operations.")
    private List<ResumeOperationTO> operations;

    public CreditInformationTO() {
        this.operations = new ArrayList<>();
    }

}
