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
@Entity(name = "TAB_PATRIMONIAL_BALANCE")
@SequenceGenerator(name = "gen_patrimonial_balance", sequenceName = "seq_patrimonial_balance", allocationSize = 1)
public class PatrimonialBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_patrimonial_balance")
    private Long id;

    private int year;

    @Column(name = "active_value")
    private double activeValue;

    @Column(name = "active_total")
    private double activeTotal;

    @Column(name = "liquid_patrimony")
    private double liquidPatrimony;

    @Column(name = "liquid_patrimony_controller")
    private double liquidPatrimonyController;

}
