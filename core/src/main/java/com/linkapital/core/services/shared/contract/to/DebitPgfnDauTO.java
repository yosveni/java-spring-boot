package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "All debit PGFN DAU data.")
@Getter
@Setter
public class DebitPgfnDauTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The total debits according to PGFN.")
    private double totalDebits;

    @ApiModelProperty(value = "The list of debit PGFN.")
    private List<DebitPgfnTO> debitPgfns;

    public DebitPgfnDauTO() {
        this.debitPgfns = new ArrayList<>();
    }

}
