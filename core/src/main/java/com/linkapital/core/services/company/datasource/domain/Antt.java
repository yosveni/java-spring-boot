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
@Entity(name = "TAB_ANTT")
@SequenceGenerator(name = "gen_antt", sequenceName = "seq_antt", allocationSize = 1)
public class Antt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_antt")
    private Long id;

    private String category;

    @Column(name = "rntrc_number")
    private String rntrcNumber;

    private String situation;

    private String municipality;

    private String uf;

    @Column(name = "emission_date")
    private Date emissionDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
