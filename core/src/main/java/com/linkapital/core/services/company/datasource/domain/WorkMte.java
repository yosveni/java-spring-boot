package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_WORK_MTE")
@SequenceGenerator(name = "gen_work_mte", sequenceName = "seq_work_mte", allocationSize = 1)
public class WorkMte {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_work_mte")
    private Long id;

    @Column(name = "fiscal_action_year")
    private int fiscalActionYear;

    @Column(name = "quantity_workers")
    private int quantityWorkers;

    @Column(name = "provenance_decision_date")
    private Date provenanceDecisionDate;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

}
