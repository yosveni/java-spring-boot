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
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_PROCON")
@SequenceGenerator(name = "gen_procon", sequenceName = "seq_procon", allocationSize = 1)
public class Procon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_procon")
    private Long id;

    private String name;

    @Column(name = "group_penalty_value")
    private double groupPenaltyValue;

    @Column(name = "total_penalty_value")
    private double totalPenaltyValue;

    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "procon_id")
    private List<ProconGroup> proconGroups;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "procon_id")
    private List<ProconProcesses> proconProcesses;

    public Procon() {
        this.proconGroups = new ArrayList<>();
        this.proconProcesses = new ArrayList<>();
    }

}
