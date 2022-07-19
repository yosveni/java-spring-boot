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
@Entity(name = "TAB_AIRCRAFT")
@SequenceGenerator(name = "gen_aircraft", sequenceName = "seq_aircraft", allocationSize = 1)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_aircraft")
    private Long id;

    private String maker;

    private String registration;

    private String model;

    private String situation;

    @Column(name = "status_rab")
    private String statusRAB;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "production_year")
    private int productionYear;

}
