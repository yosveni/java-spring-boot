package com.linkapital.core.services.person.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "TAB_CORPORATE_PARTICIPATION")
@SequenceGenerator(name = "gen_corporate_participation", sequenceName = "seq_corporate_participation", allocationSize = 1)
public class CorporateParticipation {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_corporate_participation")
    private Long id;

    private String cnpj;

    @Column(name = "social_reason", columnDefinition = "TEXT")
    private String socialReason;

    @Column(name = "description_cnae", columnDefinition = "TEXT")
    private String descriptionCnae;

    @Column(name = "branch_activity_cnae")
    private String businessActivityCnae;

    @Column(name = "social_capital")
    private String socialCapital;

    private String situation;

    @Column(name = "estimated_billing")
    private String estimatedBilling;

    @Column(name = "estimated_billing_group")
    private String estimatedBillingGroup;

    private String municipality;

    private String uf;

    private String qualification;

    @Column(name = "qualification_rf")
    private String qualificationRF;

    @Column(name = "level_preparation")
    private String levelPreparation;

    @Column(name = "level_preparation_rf")
    private String levelPreparationRF;

    private double participation;

    @Column(name = "participation_rf")
    private double participationRF;

    @Column(name = "participation_social_capital")
    private double participationSocialCapital;

    @Column(name = "participation_social_capital_rf")
    private double participationSocialCapitalRF;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    public CorporateParticipation withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CorporateParticipation withSocialReason(String socialReason) {
        setSocialReason(socialReason);
        return this;
    }

    public CorporateParticipation withDescriptionCnae(String descriptionCnae) {
        setDescriptionCnae(descriptionCnae);
        return this;
    }

    public CorporateParticipation withBusinessActivityCnae(String businessActivityCnae) {
        setBusinessActivityCnae(businessActivityCnae);
        return this;
    }

    public CorporateParticipation withSocialCapital(String socialCapital) {
        setSocialCapital(socialCapital);
        return this;
    }

    public CorporateParticipation withEstimatedBilling(String estimatedBilling) {
        setEstimatedBilling(estimatedBilling);
        return this;
    }

    public CorporateParticipation withEstimatedBillingGroup(String estimatedBillingGroup) {
        setEstimatedBillingGroup(estimatedBillingGroup);
        return this;
    }

    public CorporateParticipation withMunicipality(String municipality) {
        setMunicipality(municipality);
        return this;
    }

    public CorporateParticipation withUf(String uf) {
        setUf(uf);
        return this;
    }

    public CorporateParticipation withSituation(String situation) {
        setSituation(situation);
        return this;
    }

    public CorporateParticipation withQualification(String qualification) {
        setQualification(qualification);
        return this;
    }

    public CorporateParticipation withQualificationRF(String qualificationRF) {
        setQualificationRF(qualificationRF);
        return this;
    }

    public CorporateParticipation withLevelPreparation(String levelPreparation) {
        setLevelPreparation(levelPreparation);
        return this;
    }

    public CorporateParticipation withLevelPreparationRF(String levelPreparationRF) {
        setLevelPreparationRF(levelPreparationRF);
        return this;
    }

    public CorporateParticipation withParticipation(double participation) {
        setParticipation(participation);
        return this;
    }

    public CorporateParticipation withParticipationRF(double participationRF) {
        setParticipationRF(participationRF);
        return this;
    }

    public CorporateParticipation withParticipationSocialCapital(double participationSocialCapital) {
        setParticipationSocialCapital(participationSocialCapital);
        return this;
    }

    public CorporateParticipation withParticipationSocialCapitalRF(double participationSocialCapitalRF) {
        setParticipationSocialCapitalRF(participationSocialCapitalRF);
        return this;
    }

    public CorporateParticipation withOpeningDate(LocalDateTime openingDate) {
        setOpeningDate(openingDate);
        return this;
    }

}
