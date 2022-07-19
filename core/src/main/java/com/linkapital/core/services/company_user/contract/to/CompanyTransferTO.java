package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The data to transfer a company to another user.")
@Getter
@Setter
public class CompanyTransferTO {

    @ApiModelProperty(value = "The cnpj", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The id of the user who is related to the company", required = true)
    @Min(1)
    private long userIdProperty;

    @ApiModelProperty(value = "The id of the user to transfer the company", required = true)
    @Min(1)
    private long userIdToTransfer;

}
