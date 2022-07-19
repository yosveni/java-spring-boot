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
@Entity(name = "TAB_HEALTH_ESTABLISHMENT")
@SequenceGenerator(name = "gen_health_establishment", sequenceName = "seq_health_establishment", allocationSize = 1)
public class HealthEstablishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_health_establishment")
    private Long id;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "quantity_beds")
    private int quantityBeds;

    @Column(name = "quantity_professionals")
    private int quantityProfessionals;

    @Column(name = "last_update")
    private Date lastUpdate;

}
