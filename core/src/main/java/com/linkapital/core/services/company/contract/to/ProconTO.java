package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The procon data.")
@Getter
@Setter
public class ProconTO {

    @ApiModelProperty(value = "The name value.")
    private String name;

    @ApiModelProperty(value = "The total penalty value.")
    private double totalPenaltyValue;

    @ApiModelProperty(value = "The group penalty value.")
    private double groupPenaltyValue;

    @ApiModelProperty(value = "The update date value.")
    private Date updateDate;

    @ApiModelProperty(value = "The procon Groups list value.")
    private List<ProconGroupTO> proconGroups;

    @ApiModelProperty(value = "The procon processes list value.")
    private List<ProconProcessesTO> proconProcesses;

    public ProconTO() {
        this.proconGroups = new ArrayList<>();
        this.proconProcesses = new ArrayList<>();
    }


}
