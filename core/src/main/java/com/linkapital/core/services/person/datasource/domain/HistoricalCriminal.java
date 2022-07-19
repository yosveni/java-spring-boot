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
@Entity(name = "TAB_HISTORICAL_CRIMINAL")
@SequenceGenerator(name = "gen_historical_criminal", sequenceName = "seq_historical_criminal", allocationSize = 1)
public class HistoricalCriminal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_historical_criminal")
    private Long id;

    private String status;

    private String situation;

    private String protocol;

    @Column(name = "consultation_date")
    private Date consultationDate;

}
