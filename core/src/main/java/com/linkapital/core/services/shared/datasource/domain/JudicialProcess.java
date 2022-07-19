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
@Entity(name = "TAB_JUDICIAL_PROCESS")
@SequenceGenerator(name = "gen_judicial_process", sequenceName = "seq_judicial_process", allocationSize = 1)
public class JudicialProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_judicial_process")
    private Long id;

    @Column(name = "total_active_value")
    private double totalActiveValue;

    @Column(name = "total_passive_value")
    private double totalPassiveValue;

    @Column(name = "total_others_value")
    private double totalOthersValue;

    @Column(name = "total_value")
    private double totalValue;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "judicial_process_id")
    private List<JudicialProcessQuantity> judicialProcessQuantities;

    public JudicialProcess() {
        this.judicialProcessQuantities = new ArrayList<>();
    }

}
