package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.sped.contract.to.SpedTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to create the SPED record.")
@Getter
@Setter
public class CompanySpedTO {

    @ApiModelProperty(value = "The CNPJ of the company.")
    private String cnpj;

    @ApiModelProperty(value = "The speds data.")
    private List<SpedTO> speds;

    public CompanySpedTO() {
        this.speds = new ArrayList<>();
    }

    public CompanySpedTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanySpedTO withSpeds(List<SpedTO> speds) {
        setSpeds(speds);
        return this;
    }

}
