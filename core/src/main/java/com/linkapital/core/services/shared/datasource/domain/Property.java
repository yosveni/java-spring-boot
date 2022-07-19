package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static org.apache.commons.lang3.RandomUtils.nextLong;

@Getter
@Setter
@Entity(name = "TAB_PROPERTY")
@SequenceGenerator(name = "gen_property", sequenceName = "seq_property", allocationSize = 1)
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_property")
    private Long id;

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "building_data")
    private String buildingData;

    @Column(name = "reference_property")
    private long referenceProperty;

    @Column(name = "evaluation_value")
    private double evaluationValue;

    @Column(name = "built_area")
    private double builtArea;

    @Column(name = "ground_area")
    private double groundArea;

    @OneToOne(orphanRemoval = true, cascade = {PERSIST, REFRESH})
    @JoinColumn(name = "address")
    private Address address;

    public Property() {
        this.referenceProperty = nextLong(1, 1000000);
    }

}
