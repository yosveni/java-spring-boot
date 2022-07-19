package com.linkapital.core.services.offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(description = "The offer data to accept.")
@Getter
@Setter
public class AcceptOfferTO {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The list of offer id.", required = true)
    @NotEmpty
    private List<Long> ids;

}
