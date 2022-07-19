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
@Entity(name = "TAB_PAT_MODALITY")
@SequenceGenerator(name = "gen_pat_modality", sequenceName = "seq_pat_modality", allocationSize = 1)
public class PatModality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_pat_modality")
    private Long id;

    @Column(name = "provider_cnpj")
    private String providerCnpj;

    @Column(name = "provider_social_season")
    private String providerSocialReason;

    private String mode;

    @Column(name = "over_sm")
    private int over5Sm;

    @Column(name = "to_sm")
    private int to5Sm;

    @Column(name = "benefited_employees")
    private int benefitedEmployees;

}
