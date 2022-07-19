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
@Entity(name = "TAB_CEIS")
@SequenceGenerator(name = "gen_ceis", sequenceName = "seq_ceis", allocationSize = 1)
public class Ceis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ceis")
    private Long id;

    @Column(name = "organ_complement", columnDefinition = "TEXT")
    private String organComplement;

    @Column(name = "legal_substantiation", columnDefinition = "TEXT")
    private String legalSubstantiation;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "sanctioning_entity")
    private String sanctioningEntity;

    @Column(name = "information_entity", columnDefinition = "TEXT")
    private String informationEntity;

    @Column(columnDefinition = "TEXT")
    private String sanction;

    @Column(name = "uf_sanctioning_entity")
    private String ufSanctioningEntity;

    @Column(name = "init_sanction_date")
    private Date initSanctionDate;

    @Column(name = "end_sanction_date")
    private Date endSanctionDate;

    @Column(name = "information_date")
    private Date informationDate;

}
