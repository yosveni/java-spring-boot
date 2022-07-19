package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The financial and vertical analysis.")
@Getter
@Setter
public class AnalysisTO {

    @ApiModelProperty(value = "The date.")
    private LocalDate date;

    @ApiModelProperty(value = "The financial analysis.")
    private FinancialAnalysisTO financialAnalysis;

    @ApiModelProperty(value = "The horizontal and vertical analysis.")
    private List<VerticalAnalysisTO> verticalAnalysis;

    public AnalysisTO() {
        this.verticalAnalysis = new ArrayList<>();
    }

    public AnalysisTO withDate(LocalDate date) {
        setDate(date);
        return this;
    }

    public AnalysisTO withFinancialAnalysis(FinancialAnalysisTO financialAnalysis) {
        setFinancialAnalysis(financialAnalysis);
        return this;
    }

    public AnalysisTO withVerticalAnalysis(List<VerticalAnalysisTO> verticalAnalysis) {
        setVerticalAnalysis(verticalAnalysis);
        return this;
    }

}
