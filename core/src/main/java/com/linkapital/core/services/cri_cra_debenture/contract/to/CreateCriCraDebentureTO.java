package com.linkapital.core.services.cri_cra_debenture.contract.to;

import com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel(description = "The CRI CRA Debenture data.")
@Getter
@Setter
public class CreateCriCraDebentureTO {

    @ApiModelProperty(value = "The code.")
    private String code;

    @ApiModelProperty(value = "The debtor or issuer.")
    private String debtorIssuer;

    @ApiModelProperty(value = "The insurance carrier or sector.")
    private String insuranceSector;

    @ApiModelProperty(value = "The serie and issue.")
    private String seriesIssue;

    @ApiModelProperty(value = "The remuneration (Debenture)")
    private String remuneration;

    @ApiModelProperty(value = "The indicative value of ANBIMA.")
    private double indicativeValue;

    @ApiModelProperty(value = "The series volume on issue date.")
    private double seriesVolumeOnIssueDate;

    @ApiModelProperty(value = "The PU PAR value (Debenture).")
    private double puParDebenture;

    @ApiModelProperty(value = "The issue date in format YYYY-MM-DD.")
    private LocalDate issueDate;

    @ApiModelProperty(value = "The due date in format YYYY-MM-DD.")
    private LocalDate dueDate;

    @ApiModelProperty(value = "The type (CRI CRA or DEBENTURE).")
    private CriCraDebentureType type;

}
