package com.linkapital.core.services.cri_cra_debenture.datasource.domain;

import com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_CRI_CRA_DEBENTURE")
@SequenceGenerator(name = "gen_cri_cra_debenture", sequenceName = "seq_cri_cra_debenture", allocationSize = 1)
public class CriCraDebenture {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_cri_cra_debenture")
    private Long id;

    private String code;

    @Column(name = "debtor_issuer")
    private String debtorIssuer;

    @Column(name = "insurance_sector")
    private String insuranceSector;

    @Column(name = "series_issue")
    private String seriesIssue;

    private String remuneration;

    @Column(name = "indicative_value")
    private double indicativeValue;

    @Column(name = "series_volume_on_issue_date")
    private double seriesVolumeOnIssueDate;

    @Column(name = "pu_par_debenture")
    private double puParDebenture;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private CriCraDebentureType type;

}
