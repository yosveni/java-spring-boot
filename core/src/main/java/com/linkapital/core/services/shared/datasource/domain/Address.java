package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_ADDRESS")
@SequenceGenerator(name = "gen_address", sequenceName = "seq_address", allocationSize = 1)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_address")
    private Long id;

    private String neighborhood;

    @Column(name = "original_neighborhood")
    private String originalNeighborhood;

    private String zip;

    private String address1;

    private String address2;

    @Column(name = "m_region")
    private String mRegion;

    @Column(name = "micro_region")
    private String microRegion;

    private String region;

    private String country;

    @Column(name = "code_country")
    private String codeCountry;

    private String municipality;

    @Column(name = "code_municipality")
    private String codeMunicipality;

    @Column(name = "border_municipality")
    private String borderMunicipality;

    private String number;

    private String precision;

    private String uf;

    @Column(name = "registry_uf")
    private String registryUf;

    @Column(name = "building_type")
    private String buildingType;

    @Column(name = "formatted_address", columnDefinition = "TEXT")
    private String formattedAddress;

    private Double latitude;

    private Double longitude;

    @Column(name = "delivery_restriction")
    private boolean deliveryRestriction;

    @Column(name = "residential_address")
    private boolean residentialAddress;

    @Column(name = "latest_address")
    private boolean latestAddress;

    @Column(name = "collective_building")
    private boolean collectiveBuilding;

    @Column(name = "rf_phone")
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "TAB_ADDRESS_RF_PHONES")
    private List<String> rfPhones;

    public Address() {
        this.rfPhones = new ArrayList<>();
    }

}
