package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.shared.datasource.domain.PropertyRural;
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
@Entity(name = "TAB_COMPANY_PROPERTY_RURAL")
@SequenceGenerator(name = "gen_company_property_rural", sequenceName = "seq_company_property_rural", allocationSize = 1)
public class CompanyPropertyRural {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_property_rural")
    private Long id;

    @Column(name = "quantity_condominiums")
    private int quantityCondominiums;

    @Column(name = "quantity_holder")
    private int quantityHolder;

    @Column(name = "total_area")
    private double totalArea;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_property_rural_id")
    private List<PropertyRural> propertiesRural;

    public CompanyPropertyRural() {
        this.propertiesRural = new ArrayList<>();
    }

}
