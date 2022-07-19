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
@Entity(name = "TAB_DEBIT_PGFN_DAU")
@SequenceGenerator(name = "gen_debit_pgfn_dau", sequenceName = "seq_debit_pgfn_dau", allocationSize = 1)
public class DebitPgfnDau {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_debit_pgfn_dau")
    private Long id;

    @Column(name = "total_debits")
    private double totalDebits;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_pgfn_dau_id", nullable = false)
    private List<DebitPgfn> debitPgfns;

    public DebitPgfnDau() {
        this.debitPgfns = new ArrayList<>();
    }

}
