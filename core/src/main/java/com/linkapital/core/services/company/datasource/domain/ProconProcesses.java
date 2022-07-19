package com.linkapital.core.services.company.datasource.domain;

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
@Entity(name = "TAB_PROCON_PROCESSES")
@SequenceGenerator(name = "gen_procon_processes", sequenceName = "seq_procon_processes", allocationSize = 1)
public class ProconProcesses {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_procon_processes")
    private Long id;

    @Column(name = "process_number")
    private String processNumber;

    private String status;

    @Column(name = "penalty_value")
    private double penaltyValue;

}
