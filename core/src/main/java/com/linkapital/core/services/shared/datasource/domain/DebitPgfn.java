package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_DEBIT_PGFN")
@SequenceGenerator(name = "gen_debit_pgfn", sequenceName = "seq_debit_pgfn", allocationSize = 1)
public class DebitPgfn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_debit_pgfn")
    private Long id;

    private String nature;

    @Column(name = "inscription_number")
    private String inscriptionNumber;

    private double debit;

}
