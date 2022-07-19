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
@Entity(name = "TAB_TAX_HEALTH")
@SequenceGenerator(name = "gen_tax_health", sequenceName = "seq_tax_health", allocationSize = 1)
public class TaxHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_tax_health")
    private Long id;

    @Column(name = "tax_health")
    private String taxHealth;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_health_id")
    private List<Cnds> cnds;

    public TaxHealth() {
        this.cnds = new ArrayList<>();
    }

    public TaxHealth withTaxHealth(String taxHealth) {
        setTaxHealth(taxHealth);
        return this;
    }

}
