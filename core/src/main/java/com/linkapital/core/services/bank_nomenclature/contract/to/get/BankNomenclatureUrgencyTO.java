package com.linkapital.core.services.bank_nomenclature.contract.to.get;

import com.linkapital.core.services.bank_nomenclature.contract.enums.BankDocumentUrgency;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "The bank nomenclature data.")
@Getter
@Setter
public class BankNomenclatureUrgencyTO implements Serializable {

    @ApiModelProperty(value = "The id.")
    private Long id;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.", required = true)
    @NotNull
    @Min(1)
    @Max(4)
    private int area;

    @ApiModelProperty(value = "The urgency category.", required = true)
    @NotNull
    private BankDocumentUrgency urgency;

}
