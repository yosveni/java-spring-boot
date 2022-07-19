package com.linkapital.core.services.sped.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_SPED_DEMONSTRATION")
@DiscriminatorValue("DEMONSTRATION")
public class SpedDemonstration extends SpedPattern {

    @Override
    public SpedDemonstration withCode(String code) {
        setCode(code);
        return this;
    }

    @Override
    public SpedDemonstration withCodeDescription(String codeDescription) {
        setCodeDescription(codeDescription);
        return this;
    }

    @Override
    public SpedDemonstration withCodeType(String codeType) {
        setCodeType(codeType);
        return this;
    }

    @Override
    public SpedDemonstration withCodeSynthetic(String codeSynthetic) {
        setCodeSynthetic(codeSynthetic);
        return this;
    }

    @Override
    public SpedDemonstration withCodeNature(String codeNature) {
        setCodeNature(codeNature);
        return this;
    }

    @Override
    public SpedDemonstration withEndValueSituation(String endValueSituation) {
        setEndValueSituation(endValueSituation);
        return this;
    }

    @Override
    public SpedDemonstration withCodeLevel(int codeLevel) {
        setCodeLevel(codeLevel);
        return this;
    }

    @Override
    public SpedDemonstration withEndValue(double endValue) {
        setEndValue(endValue);
        return this;
    }

    @Override
    public SpedDemonstration withCreated(LocalDateTime created) {
        setCreated(created);
        return this;
    }

    @Override
    public SpedDemonstration withModified(LocalDateTime modified) {
        setModified(modified);
        return this;
    }

}
