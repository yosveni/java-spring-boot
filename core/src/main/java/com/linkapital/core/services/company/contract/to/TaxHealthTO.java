package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The tax health data.")
@Getter
@Setter
public class TaxHealthTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The tax health.")
    private String taxHealth;

    @ApiModelProperty(value = "The list of negative debts certificate.")
    private List<CndsTO> cnds;

    public TaxHealthTO() {
        this.cnds = new ArrayList<>();
    }

}
