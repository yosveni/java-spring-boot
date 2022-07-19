package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The cafir data.")
@Getter
@Setter
public class CafirTO {

    @ApiModelProperty(value = "The total area.")
    private double totalArea;

    @ApiModelProperty(value = "the quantity condominiums.")
    private int quantityCondominiums;

    @ApiModelProperty(value = "the quantity holder.")
    private int quantityHolder;

    @ApiModelProperty(value = "The properties rural values.")
    private List<PropertyRuralTO> propertiesRural;

    public CafirTO() {
        this.propertiesRural = new ArrayList<>();
    }

}
