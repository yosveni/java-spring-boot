package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "The request offer.")
@Getter
@Setter
public class RequestOfferTO {

    @ApiModelProperty(value = "The company cnpj", required = true)
    @NotBlank
    private String cnpj;

    @ApiModelProperty(value = "The indicative offer number 1 (Product A), 2 (Product D), 3 (Product R), 4 (Product I)", required = true)
    @NotEmpty
    private List<@NotNull @Min(1) @Max(4) Integer> numbers;

}
