package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Data of the offers to accept.")
@Getter
@Setter
public class SelectIndicativeOfferTO {

    @ApiModelProperty(value = "The cnpj company.")
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The offer one is accepted.")
    private boolean offerOne;

    @ApiModelProperty(value = "The offer two is accepted.")
    private boolean offerTwo;

    @ApiModelProperty(value = "The offer three is accepted.")
    private boolean offerThree;

    @ApiModelProperty(value = "The offer four is accepted.")
    private boolean offerFour;

    public SelectIndicativeOfferTO withOfferOne(boolean offerOne) {
        setOfferOne(offerOne);
        return this;
    }

    public SelectIndicativeOfferTO withOfferTwo(boolean offerTwo) {
        setOfferTwo(offerTwo);
        return this;
    }

    public SelectIndicativeOfferTO withOfferThree(boolean offerThree) {
        setOfferThree(offerThree);
        return this;
    }

    public SelectIndicativeOfferTO withOfferFour(boolean offerFour) {
        setOfferFour(offerFour);
        return this;
    }

}
