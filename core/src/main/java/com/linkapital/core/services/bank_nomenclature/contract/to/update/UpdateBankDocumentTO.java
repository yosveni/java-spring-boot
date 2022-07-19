package com.linkapital.core.services.bank_nomenclature.contract.to.update;

import com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The bank document data to update.")
@Getter
@Setter
public class UpdateBankDocumentTO {

    @ApiModelProperty(value = "The bank document id.", required = true)
    @Min(1)
    private long bankDocumentId;

    @ApiModelProperty(value = "The user id.")
    @Min(1)
    private Long userId;

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The description state.")
    private String descriptionState;

    @ApiModelProperty(value = "The state.", required = true)
    @NotNull
    private CompanyBankDocumentState state;

}
