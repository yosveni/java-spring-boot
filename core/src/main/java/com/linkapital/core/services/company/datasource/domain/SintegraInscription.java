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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_SINTEGRA_INSCRIPTION")
@SequenceGenerator(name = "gen_sintegra_inscription", sequenceName = "seq_sintegra_inscription", allocationSize = 1)
public class SintegraInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_sintegra_inscription")
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "registration_situation")
    private String registrationSituation;

    private String regime;

    private String email;

    private String phone;

    private String uf;

    @Column(name = "registration_situation_date")
    private Date registrationSituationDate;

}
