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
@Entity(name = "TAB_SUFRAMA")
@SequenceGenerator(name = "gen_suframa", sequenceName = "seq_suframa", allocationSize = 1)
public class Suframa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_suframa")
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "registration_situation")
    private String registrationSituation;

    private String icms;

    private String ipi;

    @Column(name = "pis_cofins")
    private String pisCofins;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
