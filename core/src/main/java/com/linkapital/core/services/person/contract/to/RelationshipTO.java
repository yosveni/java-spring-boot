package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data of the relationship.")
@Getter
@Setter
public class RelationshipTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The person cpf.")
    private String cpf;

    @ApiModelProperty(value = "The person name.")
    private String name;

    @ApiModelProperty(value = "The description.")
    private String description;

}
