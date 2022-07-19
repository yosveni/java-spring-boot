package com.linkapital.core.services.sped.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "sped_type", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(name = "gen_sped_pattern", sequenceName = "seq_sped_pattern", allocationSize = 1)
public abstract class SpedPattern implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_sped_pattern")
    private Long id;

    private String code;

    @Column(name = "code_description")
    private String codeDescription;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "code_synthetic")
    private String codeSynthetic;

    @Column(name = "code_nature")
    private String codeNature;

    @Column(name = "end_value_situation")
    private String endValueSituation;

    @Column(name = "code_level")
    private int codeLevel;

    @Column(name = "end_value")
    private double endValue;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @PrePersist
    private void preSave() {
        this.created = now();
        this.modified = this.created;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = now();
    }

    public SpedPattern withCode(String code) {
        this.code = code;
        return this;
    }

    public SpedPattern withCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
        return this;
    }

    public SpedPattern withCodeType(String codeType) {
        this.codeType = codeType;
        return this;
    }

    public SpedPattern withCodeSynthetic(String codeSynthetic) {
        this.codeSynthetic = codeSynthetic;
        return this;
    }

    public SpedPattern withCodeNature(String codeNature) {
        this.codeNature = codeNature;
        return this;
    }

    public SpedPattern withEndValueSituation(String endValueSituation) {
        this.endValueSituation = endValueSituation;
        return this;
    }

    public SpedPattern withCodeLevel(int codeLevel) {
        this.codeLevel = codeLevel;
        return this;
    }

    public SpedPattern withEndValue(double endValue) {
        this.endValue = endValue;
        return this;
    }

    public SpedPattern withCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public SpedPattern withModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

}
