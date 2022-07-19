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
@Entity(name = "TAB_SIMPLE_NATIONAL")
@SequenceGenerator(name = "gen_simple_national", sequenceName = "seq_simple_national", allocationSize = 1)
public class SimpleNational {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_simple_national")
    private Long id;

    private boolean simei;

    private boolean simple;

    @Column(name = "simple_irregular")
    private boolean simpleIrregular;

    @Column(name = "simei_date")
    private Date simeiDate;

    @Column(name = "simple_date")
    private Date simpleDate;

}
