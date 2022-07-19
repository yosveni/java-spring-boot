package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The procon group data.")
@Getter
@Setter
public class ProconGroupTO {

    @ApiModelProperty(value = "The cnpj value.")
    private String cnpj;

    @ApiModelProperty(value = "The total penalty value.")
    private double totalPenaltyValue;

}
