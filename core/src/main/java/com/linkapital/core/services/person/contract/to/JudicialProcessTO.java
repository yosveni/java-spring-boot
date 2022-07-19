package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The judicial process data.")
@Getter
@Setter
public class JudicialProcessTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The total active value.")
    private double totalActiveValue;

    @ApiModelProperty(value = "The total passive value.")
    private double totalPassiveValue;

    @ApiModelProperty(value = "The total others value.")
    private double totalOthersValue;

    @ApiModelProperty(value = "The total value.")
    private double totalValue;

    @ApiModelProperty(value = "The list of details of judicial process.")
    private List<JudicialProcessQuantityTO> judicialProcessQuantities;

    public JudicialProcessTO() {
        this.judicialProcessQuantities = new ArrayList<>();
    }

}
