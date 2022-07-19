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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_CNEP")
@SequenceGenerator(name = "gen_cnep", sequenceName = "seq_cnep", allocationSize = 1)
public class Cnep {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnep")
    private Long id;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "sanctioning_entity")
    private String sanctioningEntity;

    @Column(name = "uf_sanctioning_entity")
    private String ufSanctioningEntity;

    @Column(columnDefinition = "TEXT")
    private String sanction;

    @Column(name = "penalty_value")
    private double penaltyValue;

    @Column(name = "init_sanction_date")
    private Date initSanctionDate;

    @Column(name = "end_sanction_date")
    private Date endSanctionDate;

}
