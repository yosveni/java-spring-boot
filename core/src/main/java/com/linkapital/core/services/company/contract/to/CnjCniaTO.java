package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The cnjCnia data.")
@Getter
@Setter
public class CnjCniaTO {

    @ApiModelProperty(value = "The sphere value.")
    private String sphere;

    @ApiModelProperty(value = "The process number value.")
    private String processNumber;

    @ApiModelProperty(value = "The description entity value.")
    private String descriptionEntity;

    @ApiModelProperty(value = "The uf value.")
    private String uf;

    @ApiModelProperty(value = "The value.")
    private double value;

    @ApiModelProperty(value = "The registration date value.")
    private Date registrationDate;

    @ApiModelProperty(value = "The related issues value.")
    private List<String> relatedIssues;

    public CnjCniaTO() {
        this.relatedIssues = new ArrayList<>();
    }

}
