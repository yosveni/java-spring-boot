package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The workMte data.")
@Getter
@Setter
public class WorkMteTO {

    @ApiModelProperty(value = "The fiscal action year.")
    private int fiscalActionYear;

    @ApiModelProperty(value = "The quantity workers.")
    private int quantityWorkers;

    @ApiModelProperty(value = "The provenance decision date.")
    private Date provenanceDecisionDate;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

}
