package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel(description = "The cnds data.")
@Getter
@Setter
public class CndsTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The emitterName.")
    private String emitterName;

    @ApiModelProperty(value = "The situation.")
    private String situation;

    @ApiModelProperty(value = "The certificate number.")
    private String certificateNumber;

    @ApiModelProperty(value = "The emission date.")
    private LocalDate emissionDate;

    @ApiModelProperty(value = "The expiration date.")
    private LocalDate expirationDate;

    @ApiModelProperty(value = "The document.")
    private DirectoryTO document;

}
