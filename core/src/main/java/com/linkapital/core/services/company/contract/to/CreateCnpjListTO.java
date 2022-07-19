package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The backoffice client data to logout.")
@Getter
@Setter
public class CreateCnpjListTO {

    @ApiModelProperty(value = "The cnpj list.", required = true)
    @NotEmpty
    private List<String> cnpjList;

    public CreateCnpjListTO() {
        this.cnpjList = new ArrayList<>();
    }

}
