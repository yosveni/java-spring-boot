package com.linkapital.core.services.method_k.contract.to;

import com.linkapital.core.services.company.contract.to.CnaeTO;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@ApiModel(description = "The analisys score data.")
@Getter
@Setter
public class ScoreSummaryTO {

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The fantasy name.")
    private String fantasyName;

    @ApiModelProperty(value = "The uf.")
    private String uf;

    @ApiModelProperty(value = "La quantity of employee.")
    private int quantityEmployee;

    @ApiModelProperty(value = "The strength score.")
    private int strength;

    @ApiModelProperty(value = "The register score.")
    private int register;

    @ApiModelProperty(value = "The habituality score.")
    private int habituality;

    @ApiModelProperty(value = "The opening date.")
    private LocalDateTime openingDate;

    @ApiModelProperty(value = "The main cnae.")
    private CnaeTO cnae;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "sped_analysis_id")
    private List<SpedBaseTO> balances;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "sped_analysis_id")
    private List<SpedBaseTO> demostrations;

    @ApiModelProperty(value = "The credits informations.")
    private List<CreditInformationTO> creditsInformations;

    @ApiModelProperty(value = "The score analysis.")
    private List<ScoreAnalysisTO> analysis;

    public ScoreSummaryTO() {
        this.creditsInformations = new ArrayList<>();
        this.analysis = new ArrayList<>();
    }

    public ScoreSummaryTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public ScoreSummaryTO withFantasyName(String fantasyName) {
        setFantasyName(fantasyName);
        return this;
    }

    public ScoreSummaryTO withUf(String uf) {
        setUf(uf);
        return this;
    }

    public ScoreSummaryTO withQuantityEmployee(int quantityEmployee) {
        setQuantityEmployee(quantityEmployee);
        return this;
    }

    public ScoreSummaryTO withStrength(int strength) {
        setStrength(strength);
        return this;
    }

    public ScoreSummaryTO withRegister(int register) {
        setRegister(register);
        return this;
    }

    public ScoreSummaryTO withHabituality(int habituality) {
        setHabituality(habituality);
        return this;
    }

    public ScoreSummaryTO withOpeningDate(LocalDateTime openingDate) {
        setOpeningDate(openingDate);
        return this;
    }

    public ScoreSummaryTO withCnae(CnaeTO cnae) {
        setCnae(cnae);
        return this;
    }

    public ScoreSummaryTO withBalances(List<SpedBaseTO> balances) {
        setBalances(balances);
        return this;
    }

    public ScoreSummaryTO withDemostrations(List<SpedBaseTO> demostrations) {
        setDemostrations(demostrations);
        return this;
    }

    public ScoreSummaryTO withCreditsInformations(List<CreditInformationTO> creditsInformations) {
        setCreditsInformations(creditsInformations);
        return this;
    }

    public ScoreSummaryTO withAnalysis(List<ScoreAnalysisTO> analysis) {
        setAnalysis(analysis);
        return this;
    }

}
