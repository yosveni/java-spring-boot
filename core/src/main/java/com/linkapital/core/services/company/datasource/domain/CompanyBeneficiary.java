package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_BENEFICIARY")
@SequenceGenerator(name = "gen_company_beneficiary", sequenceName = "seq_company_beneficiary", allocationSize = 1)
public class CompanyBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_beneficiary")
    private Long id;

    private String document;

    private String name;

    private int grade;

    @Column(name = "grade_qsa")
    private int gradeQsa;

    private double participation;

    @Column(name = "participation_qsa")
    private double participationQsa;

    private boolean dead;

    public CompanyBeneficiary withDocument(String document) {
        setDocument(document);
        return this;
    }

    public CompanyBeneficiary withName(String name) {
        setName(name);
        return this;
    }

    public CompanyBeneficiary withGrade(int grade) {
        setGrade(grade);
        return this;
    }

    public CompanyBeneficiary withGradeQsa(int gradeQsa) {
        setGradeQsa(gradeQsa);
        return this;
    }

    public CompanyBeneficiary withParticipation(double participation) {
        setParticipation(participation);
        return this;
    }

    public CompanyBeneficiary withParticipationQsa(double participationQsa) {
        setParticipationQsa(participationQsa);
        return this;
    }

    public CompanyBeneficiary withDead(boolean dead) {
        setDead(dead);
        return this;
    }

}
