package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company property rural data.")
@Getter
@Setter
public class CafirTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The properties of Individuals that are classified as CONDOMINO.")
    private int quantityCondominiums;

    @ApiModelProperty(value = "The properties of the Individual or Legal Entity in which they are classified as HOLDER.")
    private int quantityHolder;

    @ApiModelProperty(value = "The total area.")
    private double totalArea;

    @ApiModelProperty(value = "The properties rural.")
    private List<PropertyRuralTO> propertiesRural;

    public CafirTO() {
        this.propertiesRural = new ArrayList<>();
    }

}
