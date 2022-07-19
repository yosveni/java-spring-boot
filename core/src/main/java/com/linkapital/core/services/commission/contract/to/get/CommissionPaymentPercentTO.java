package com.linkapital.core.services.commission.contract.to.get;

import com.linkapital.core.services.commission.contract.to.create.CreateCommissionPaymentPercentTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@ApiModel(description = "The commission campaign payment percent data.")
@Getter
@Setter
public class CommissionPaymentPercentTO extends CreateCommissionPaymentPercentTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private Long id;

}
