package com.linkapital.core.services.person.datasource.domain;

import lombok.Getter;
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
@Entity(name = "TAB_DISABILITIES_BACEN")
@SequenceGenerator(name = "gen_disabilities_bacen", sequenceName = "seq_disabilities_bacen", allocationSize = 1)
public class DisabilitiesBacen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_disabilities_bacen")
    private Long id;

    private String penalty;

    private int duration;

    @Column(name = "penalty_period_date")
    private Date penaltyPeriodDate;

    @Column(name = "publication_date")
    private Date publicationDate;

}
