package com.linkapital.core.services.person.datasource.domain;

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
@Entity(name = "TAB_HISTORICAL_FUNCTIONAL")
@SequenceGenerator(name = "gen_historical_functional", sequenceName = "seq_historical_functional", allocationSize = 1)
public class HistoricalFunctional {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_historical_functional")
    private Long id;

    private String cnpj;

    @Column(name = "social_reason")
    private String socialReason;

    private int months;

    @Column(name = "admission_date")
    private Date admissionDate;

    @Column(name = "dismissed_date")
    private Date dismissedDate;

}
