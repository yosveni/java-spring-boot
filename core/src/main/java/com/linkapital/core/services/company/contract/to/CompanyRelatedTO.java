package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel(description = "The company related data.")
@Getter
@Setter
public class CompanyRelatedTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The description cnae.")
    private String descriptionCnae;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The uf.")
    private String uf;

    @ApiModelProperty(value = "The opening date.")
    private LocalDateTime openingDate;

}
