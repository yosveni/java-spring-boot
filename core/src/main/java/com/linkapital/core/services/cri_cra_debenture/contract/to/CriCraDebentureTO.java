package com.linkapital.core.services.cri_cra_debenture.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The CRI CRA Debenture data.")
@Getter
@Setter
public class CriCraDebentureTO extends CreateCriCraDebentureTO {

    @ApiModelProperty(value = "The id.")
    private long id;

}
