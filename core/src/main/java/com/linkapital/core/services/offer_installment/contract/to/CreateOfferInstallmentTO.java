package com.linkapital.core.services.offer_installment.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to create the installment of the offer.")
@Getter
@Setter
public class CreateOfferInstallmentTO {

    @ApiModelProperty(value = "The offer id.", required = true)
    @NotNull
    private long offerId;

    @ApiModelProperty(value = "The offer id.", required = true)
    @NotEmpty
    private List<@Valid BaseOfferInstallmentTO> installments;

    public CreateOfferInstallmentTO() {
        this.installments = new ArrayList<>();
    }

}
