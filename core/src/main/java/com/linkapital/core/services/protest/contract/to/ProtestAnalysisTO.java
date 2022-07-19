package com.linkapital.core.services.protest.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(description = "The protest resume data.")
@Getter
@Setter
public class ProtestAnalysisTO {

    @ApiModelProperty(value = "The amount registry.")
    private int amountRegistry;

    @ApiModelProperty(value = "The amount protest.")
    private int amountProtest;

    @ApiModelProperty(value = "The total value.")
    private double totalValue;

    @ApiModelProperty(value = "The first protest.")
    private ProtestLightTO firstProtest;

    @ApiModelProperty(value = "The higher protest.")
    private ProtestLightTO higherValueProtest;

    @ApiModelProperty(value = "The list of amount of protests grouped by state.")
    private Map<String, Long> activesByState;

    @ApiModelProperty(value = "The list of amount of protests grouped by year.")
    private Map<String, Long> activesByYear;

    @ApiModelProperty(value = "The evolution of protests by months.")
    private List<ProtestEvolutionTO> protestEvolutionByMonths;

    @ApiModelProperty(value = "The evolution of protests by years.")
    private List<ProtestEvolutionTO> protestEvolutionByYears;

    @ApiModelProperty(value = "The protests by area.")
    private List<ProtestAreaTO> protestsByArea;

    public ProtestAnalysisTO() {
        this.activesByState = new HashMap<>();
        this.activesByYear = new HashMap<>();
        this.protestEvolutionByMonths = new ArrayList<>();
        this.protestEvolutionByYears = new ArrayList<>();
    }

    public ProtestAnalysisTO withAmountRegistry(int amountRegistry) {
        setAmountRegistry(amountRegistry);
        return this;
    }

    public ProtestAnalysisTO withAmountProtest(int amountProtest) {
        setAmountProtest(amountProtest);
        return this;
    }

    public ProtestAnalysisTO withTotalValue(double totalValue) {
        setTotalValue(totalValue);
        return this;
    }

    public ProtestAnalysisTO withFirstProtest(ProtestLightTO firstValue) {
        setFirstProtest(firstValue);
        return this;
    }

    public ProtestAnalysisTO withHigherValueProtest(ProtestLightTO higherValue) {
        setHigherValueProtest(higherValue);
        return this;
    }

    public ProtestAnalysisTO withActivesByState(Map<String, Long> activesByState) {
        setActivesByState(activesByState);
        return this;
    }

    public ProtestAnalysisTO withActivesByYear(Map<String, Long> activesByYear) {
        setActivesByYear(activesByYear);
        return this;
    }

    public ProtestAnalysisTO withProtestEvolutionByMonths(List<ProtestEvolutionTO> protestEvolutionByMonths) {
        setProtestEvolutionByMonths(protestEvolutionByMonths);
        return this;
    }

    public ProtestAnalysisTO withProtestEvolutionByYears(List<ProtestEvolutionTO> protestEvolutionByYears) {
        setProtestEvolutionByYears(protestEvolutionByYears);
        return this;
    }

    public ProtestAnalysisTO withProtestsByArea(List<ProtestAreaTO> protestByArea) {
        setProtestsByArea(protestByArea);
        return this;
    }

}
