package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.company.contract.enums.CompanySector;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "TAB_CNAE")
@SequenceGenerator(name = "gen_cnae", sequenceName = "seq_cnae", allocationSize = 1)
public class Cnae {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae")
    private Long id;

    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "business_activity")
    private String businessActivity;

    private CompanySector sector;

    @ManyToMany(mappedBy = "cnaes")
    private Set<Company> companies;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "cnae_id")
    private List<PhysicalProduction> physicalProductions;

    public Cnae() {
        this.companies = new HashSet<>();
        this.physicalProductions = new ArrayList<>();
    }

    public Cnae withCode(String code) {
        setCode(code);
        return this;
    }

    public Cnae withDescription(String description) {
        setDescription(description);
        return this;
    }

    public Cnae withBusinessActivity(String businessActivity) {
        setBusinessActivity(businessActivity);
        return this;
    }

    public Cnae withSector(CompanySector sector) {
        setSector(sector);
        return this;
    }

}
