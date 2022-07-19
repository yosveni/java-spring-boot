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
@Entity(name = "TAB_DEBIT_MTE_PROCESS")
@SequenceGenerator(name = "gen_debit_mte_process", sequenceName = "seq_debit_mte_process", allocationSize = 1)
public class DebitMteProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_debit_mte_process")
    private Long id;

    private String number;

    private String situation;

    @Column(name = "infringement_category", columnDefinition = "TEXT")
    private String infringementCategory;

    @Column(name = "infringement_capitulation", columnDefinition = "TEXT")
    private String infringementCapitulation;

}
