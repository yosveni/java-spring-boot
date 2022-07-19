package com.linkapital.core.services.sped.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_SPED_BALANCE")
@DiscriminatorValue("BALANCE")
public class SpedBalance extends SpedPattern {

    @Column(name = "init_value_situation")
    private String initValueSituation;

    @Column(name = "init_value")
    private double initValue;

    @Column(name = "credit_value")
    private double creditValue;

    @Column(name = "debit_value")
    private double debitValue;

    @Column(name = "init_date")
    private LocalDate initDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Override
    public SpedBalance withCode(String code) {
        setCode(code);
        return this;
    }

    @Override
    public SpedBalance withCodeDescription(String codeDescription) {
        setCodeDescription(codeDescription);
        return this;
    }

    @Override
    public SpedBalance withCodeType(String codeType) {
        setCodeType(codeType);
        return this;
    }

    @Override
    public SpedBalance withCodeSynthetic(String codeSynthetic) {
        setCodeSynthetic(codeSynthetic);
        return this;
    }

    @Override
    public SpedBalance withCodeNature(String codeNature) {
        setCodeNature(codeNature);
        return this;
    }

    @Override
    public SpedBalance withEndValueSituation(String endValueSituation) {
        setEndValueSituation(endValueSituation);
        return this;
    }

    @Override
    public SpedBalance withCodeLevel(int codeLevel) {
        setCodeLevel(codeLevel);
        return this;
    }

    @Override
    public SpedBalance withEndValue(double endValue) {
        setEndValue(endValue);
        return this;
    }

    @Override
    public SpedBalance withCreated(LocalDateTime created) {
        setCreated(created);
        return this;
    }

    @Override
    public SpedBalance withModified(LocalDateTime modified) {
        setModified(modified);
        return this;
    }

    public SpedBalance withInitValue(double initValue) {
        this.initValue = initValue;
        return this;
    }

    public SpedBalance withInitValueSituation(String initValueSituation) {
        this.initValueSituation = initValueSituation;
        return this;
    }

    public SpedBalance withCreditValue(double creditValue) {
        this.creditValue = creditValue;
        return this;
    }

    public SpedBalance withDebitValue(double debitValue) {
        this.debitValue = debitValue;
        return this;
    }

    public SpedBalance withInitDate(LocalDate initDate) {
        setInitDate(initDate);
        return this;
    }

    public SpedBalance withEndDate(LocalDate endDate) {
        setEndDate(endDate);
        return this;
    }

}
