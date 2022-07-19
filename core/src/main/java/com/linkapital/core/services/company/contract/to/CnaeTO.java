package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company.contract.enums.CompanySector;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The cnae data.")
@Getter
@Setter
public class CnaeTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The code.")
    private String code;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The business activity.")
    private String businessActivity;

    @ApiModelProperty(value = "The business company sector.")
    private CompanySector sector;

}
