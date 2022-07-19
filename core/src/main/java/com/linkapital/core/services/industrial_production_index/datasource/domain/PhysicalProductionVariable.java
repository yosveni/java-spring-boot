package com.linkapital.core.services.industrial_production_index.datasource.domain;

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
@Entity(name = "TAB_PHYSICAL_PRODUCTION_VARIABLE")
@SequenceGenerator(name = "gen_physical_production_variable", sequenceName = "seq_physical_production_variable",
        allocationSize = 1)
public class PhysicalProductionVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_physical_production_variable")
    private Long id;

    private String name;

    @Column(name = "measure_unit")
    private String measureUnit;

    private double value;

}
