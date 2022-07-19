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
public class DomainTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The title and / or name of registered software.")
    private String name;

    @ApiModelProperty(value = "The name of the individual responsible for registering and / or creating the website for the company.")
    private String responsible;

    @ApiModelProperty(value = "The registered website creation date.")
    private Date createdDate;

    @ApiModelProperty(value = "The modification date.")
    private Date modificationDate;

    @ApiModelProperty(value = "The expiration date.")
    private Date expirationDate;

}
