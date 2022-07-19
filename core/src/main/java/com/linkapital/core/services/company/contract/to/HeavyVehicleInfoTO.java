package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The heavy vehicle data.")
@Getter
@Setter
public class HeavyVehicleInfoTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The number of heavy vehicles of that CNPJ that were manufactured until 1 year ago..")
    private int upto1;

    @ApiModelProperty(value = "The number of heavy vehicles of that CNPJ that were manufactured more than 10 years ago.")
    private int over10;

    @ApiModelProperty(value = "The number of heavy vehicles of that CNPJ that were manufactured between 2 and 5 years ago.")
    private int between2And5;

    @ApiModelProperty(value = "The number of heavy vehicles of that CNPJ that were manufactured between 5 and 10 years ago.")
    private int between5And10;

    @ApiModelProperty(value = "The sum of the CNPJ's heavy vehicles of the matrix + vehicles of the branches that were manufactured until 1 year ago.")
    private int groupUpTo1;

    @ApiModelProperty(value = "The sum of the CNPJ's heavy vehicles of the matrix + vehicles of the branches that were manufactured between 2 and 5 years ago.")
    private int groupBetween2And5;

    @ApiModelProperty(value = "The sum of the CNPJ's heavy vehicles of the matrix + vehicles of the branches that were manufactured between 5 and 10 years ago.")
    private int groupBetween5And10;

    @ApiModelProperty(value = "The sum of the CNPJ's heavy vehicles of the parent company + vehicles of the branches that were manufactured more than 10 years ago.")
    private int groupOver10;

    @ApiModelProperty(value = "The number of heavy vehicles registered with the company's CNPJ.")
    private int heavyVehicles;

    @ApiModelProperty(value = "The sum of heavy vehicles registered in the CNPJ of the head office and in the CNPJs of its branches.")
    private int heavyVehiclesGroup;

    @ApiModelProperty(value = "The heavy vehicle data.")
    private List<HeavyVehicleTO> vehicles;

    public HeavyVehicleInfoTO() {
        this.vehicles = new ArrayList<>();
    }

}
