package com.linkapital.core.services.cri_cra_debenture.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(description = "The CRI CRA Debenture data.")
@Getter
@Setter
public class CreateCriCraDebentureListTO {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The elements.", required = true)
    @NotEmpty
    private List<@NonNull CreateCriCraDebentureTO> elements;

}
