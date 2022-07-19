package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static org.apache.commons.lang3.RandomUtils.nextLong;

@Getter
@Setter
@Entity(name = "TAB_PROPERTY_RURAL")
@SequenceGenerator(name = "gen_property_rural", sequenceName = "seq_property_rural", allocationSize = 1)
public class PropertyRural {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_property_rural")
    private Long id;

    private String nirf;

    private String name;

    private String condominium;

    private String municipality;

    private String type;

    private String uf;

    @Column(name = "reference_property")
    private long referenceProperty;

    private double area;

    public PropertyRural() {
        this.referenceProperty = nextLong(1, 1000000);
    }

}
