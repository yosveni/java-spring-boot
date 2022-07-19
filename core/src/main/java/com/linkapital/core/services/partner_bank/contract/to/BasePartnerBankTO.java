package com.linkapital.core.services.partner_bank.contract.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BasePartnerBankTO implements Serializable {

    @ApiModelProperty(value = "The name.", required = true)
    private String name;

    @ApiModelProperty(value = "The days it takes to issue an answer.", required = true)
    private int days;

    @ApiModelProperty(value = "The numbers of learners to which the document belongs.", required = true)
    @NotEmpty
    private List<@NotNull @Min(1) @Max(4) Integer> areas;

    public BasePartnerBankTO() {
        this.areas = new ArrayList<>();
    }

}
