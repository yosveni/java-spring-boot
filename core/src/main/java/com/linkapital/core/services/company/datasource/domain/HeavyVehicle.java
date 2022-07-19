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
@Entity(name = "TAB_HEAVY_VEHICLE")
@SequenceGenerator(name = "gen_heavy_vehicle", sequenceName = "seq_heavy_vehicle", allocationSize = 1)
public class HeavyVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_heavy_vehicle")
    private Long id;

    private String fuel;

    private String model;

    @Column(name = "car_plate", columnDefinition = "TEXT")
    private String carPlate;

    private String renavam;

    private String type;

    private String uf;

    private boolean antt;

    @Column(name = "production_year")
    private int productionYear;

}
