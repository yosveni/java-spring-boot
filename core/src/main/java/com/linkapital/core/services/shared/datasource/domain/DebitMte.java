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
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_DEBIT_MTE")
@SequenceGenerator(name = "gen_debit_mte", sequenceName = "seq_debit_mte", allocationSize = 1)
public class DebitMte {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_debit_mte")
    private Long id;

    private String code;

    @Column(name = "debit_situation")
    private String debitSituation;

    @Column(name = "certificate_type")
    private String certificateType;

    @Column(name = "emission_date")
    private Date emissionDate;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_mte_id", nullable = false)
    private List<DebitMteProcess> processes;

    public DebitMte() {
        this.processes = new ArrayList<>();
    }

}
