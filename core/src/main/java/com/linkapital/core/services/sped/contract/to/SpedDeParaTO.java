package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The sped dePara data.")
@Getter
@Setter
public class SpedDeParaTO {

    private String code;
    private String description;
    private String codeSped7;
    private String descriptionSped7;

    public SpedDeParaTO withCode(String code) {
        setCode(code);
        return this;
    }

    public SpedDeParaTO withDescription(String description) {
        setDescription(description);
        return this;
    }

    public SpedDeParaTO withCodeSped7(String codeSped7) {
        setCodeSped7(codeSped7);
        return this;
    }

    public SpedDeParaTO withDescriptionSped7(String descriptionSped7) {
        setDescriptionSped7(descriptionSped7);
        return this;
    }

}
