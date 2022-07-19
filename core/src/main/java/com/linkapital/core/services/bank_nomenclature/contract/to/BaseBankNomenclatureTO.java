package com.linkapital.core.services.bank_nomenclature.contract.to;

import com.linkapital.core.services.bank_nomenclature.contract.enums.BankDocumentStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseBankNomenclatureTO implements Serializable {

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The fase stage.", required = true)
    private BankDocumentStage stage;

    @ApiModelProperty(value = "The extensions that the nomenclature supports.", required = true)
    @NotEmpty
    @Valid
    private List<String> extensions;

    public BaseBankNomenclatureTO() {
        this.extensions = new ArrayList<>();
    }

}
