package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_HEAVY_VEHICLE_INFO")
@SequenceGenerator(name = "gen_heavy_vehicle_info", sequenceName = "seq_heavy_vehicle_info", allocationSize = 1)
public class HeavyVehicleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_heavy_vehicle_info")
    private Long id;

    @Column(name = "up_to_1")
    private int upto1;

    @Column(name = "over_10")
    private int over10;

    @Column(name = "between_2_and_5")
    private int between2And5;

    @Column(name = "between_5_and_10")
    private int between5And10;

    @Column(name = "group_up_to_1")
    private int groupUpTo1;

    @Column(name = "group_between_2_and_5")
    private int groupBetween2And5;

    @Column(name = "group_between_5_and_10")
    private int groupBetween5And10;

    @Column(name = "group_over_10")
    private int groupOver10;

    @Column(name = "heavy_vehicles")
    private int heavyVehicles;

    @Column(name = "heavy_vehicles_group")
    private int heavyVehiclesGroup;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "heavy_vehicle_info_id")
    private List<HeavyVehicle> vehicles;

    public HeavyVehicleInfo() {
        this.vehicles = new ArrayList<>();
    }

}
