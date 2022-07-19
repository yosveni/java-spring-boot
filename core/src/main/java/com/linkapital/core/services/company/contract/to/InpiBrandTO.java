package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "All domain data.")
@Getter
@Setter
public class InpiBrandTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The brand class registered at the INPI.")
    private String classBrand;

    @ApiModelProperty(value = "The process number of the brand registration with the INPI.")
    private String processNumber;

    @ApiModelProperty(value = "The status of brand registration with the INPI.")
    private String situation;

    @ApiModelProperty(value = "The brand title.")
    private String brand;

    @ApiModelProperty(value = "The date on which the IPI (National Institute of Industrial Property) granted the right to a patent requested by the company.")
    private Date depositDate;

}
