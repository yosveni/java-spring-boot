package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to create the SPED record.")
@Getter
@Setter
public class CreateSpedTO {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The user id.")
    @Min(1)
    private Long userId;

    @ApiModelProperty(value = "The sped list.")
    private List<SpedTO> speds;

    public CreateSpedTO() {
        this.speds = new ArrayList<>();
    }

}
