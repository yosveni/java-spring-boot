package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.person.contract.to.PartnerSpouseTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to start learning 2.")
@Getter
@Setter
public class DataInitLearning2TO {

    @ApiModelProperty(value = "The cnpj of the company.")
    private String cnpj;

    @ApiModelProperty(value = "The list of cpf of partner and spouse.")
    private List<PartnerSpouseTO> partnersSpouse;

    public DataInitLearning2TO() {
        this.partnersSpouse = new ArrayList<>();
    }

    public DataInitLearning2TO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public DataInitLearning2TO withPartnersSpouse(List<PartnerSpouseTO> partnersSpouse) {
        setPartnersSpouse(partnersSpouse);
        return this;
    }

}
