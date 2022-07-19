package com.linkapital.core.services.credit_information.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "TAB_RESUME_OPERATION")
@SequenceGenerator(name = "gen_resume_operation", sequenceName = "seq_resume_operation", allocationSize = 1)
public class ResumeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_resume_operation")
    private Long id;

    private String modality;

    @Column(name = "exchange_variation")
    private String exchangeVariation;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "resume_operation_id")
    private Set<Earning> earnings;

    public ResumeOperation() {
        this.earnings = new HashSet<>();
    }

    public ResumeOperation withModality(String modality) {
        setModality(modality);
        return this;
    }

    public ResumeOperation withExchangeVariation(String exchangeVariation) {
        setExchangeVariation(exchangeVariation);
        return this;
    }

}
