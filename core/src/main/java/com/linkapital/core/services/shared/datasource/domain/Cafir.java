package com.linkapital.core.services.shared.datasource.domain;

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
@Entity(name = "TAB_CAFIR")
@SequenceGenerator(name = "gen_cafir", sequenceName = "seq_cafir", allocationSize = 1)
public class Cafir {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cafir")
    private Long id;

    @Column(name = "quantity_condominiums")
    private int quantityCondominiums;

    @Column(name = "quantity_holder")
    private int quantityHolder;

    @Column(name = "total_area")
    private double totalArea;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cafir_property_rural_id")
    private List<PropertyRural> propertiesRural;

    public Cafir() {
        this.propertiesRural = new ArrayList<>();
    }

}
