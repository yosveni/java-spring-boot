package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The sped data.")
@Getter
@Setter
public class SpedTO {

    @ApiModelProperty(value = "The demonstrative init date.")
    private LocalDate demonstrativeInitDate;

    @ApiModelProperty(value = "The demonstrative end date.")
    private LocalDate demonstrativeEndDate;

    @ApiModelProperty(value = "The sped balance list.")
    private List<SpedBalanceTO> spedBalances;

    @ApiModelProperty(value = "The sped demonstration result list.")
    private List<SpedDemonstrationTO> spedDemonstrations;

    public SpedTO() {
        this.spedBalances = new ArrayList<>();
        this.spedDemonstrations = new ArrayList<>();
    }

    public SpedTO withDemonstrativeInitDate(LocalDate demonstrativeInitDate) {
        setDemonstrativeInitDate(demonstrativeInitDate);
        return this;
    }

    public SpedTO withDemonstrativeEndDate(LocalDate demonstrativeEndDate) {
        setDemonstrativeEndDate(demonstrativeEndDate);
        return this;
    }

    public SpedTO withSpedBalances(List<SpedBalanceTO> spedBalance) {
        setSpedBalances(spedBalance);
        return this;
    }

    public SpedTO withSpedDemonstrations(List<SpedDemonstrationTO> spedDemonstrations) {
        setSpedDemonstrations(spedDemonstrations);
        return this;
    }

}
