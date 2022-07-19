package com.linkapital.core.services.credit_information.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "TAB_CREDIT_INFORMATION")
@SequenceGenerator(name = "gen_credit_information", sequenceName = "seq_credit_information", allocationSize = 1)
public class CreditInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_credit_information")
    private Long id;

    @Column(name = "cnpj_if_requester")
    private String cnpjIfRequester;

    @Column(name = "consult_date")
    private String consultDate;

    @Column(name = "count_operation")
    private int countOperation;

    @Column(name = "count_institution")
    private int countInstitution;

    @Column(name = "count_operation_sub_judice")
    private int countOperationSubJudice;

    @Column(name = "responsibility_total_sub_judice")
    private int responsibilityTotalSubJudice;

    @Column(name = "count_operation_disagreement")
    private int countOperationDisagreement;

    @Column(name = "responsibility_total_disagreement")
    private int responsibilityTotalDisagreement;

    @Column(name = "assumed_obligation")
    private double assumedObligation;

    @Column(name = "vendor_indirect_risk")
    private double vendorIndirectRisk;

    @Column(name = "percent_document_processed")
    private double percentDocumentProcessed;

    @Column(name = "percent_volume_processed")
    private double percentVolumeProcessed;

    @Column(nullable = false)
    private boolean find;

    @Column(name = "start_date_relationship")
    private Date startRelationshipDate;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "credit_information_id")
    private Set<ResumeOperation> operations;

    public CreditInformation() {
        this.find = true;
        this.operations = new HashSet<>();
    }

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public CreditInformation withFind(boolean find) {
        setFind(find);
        return this;
    }

    public CreditInformation withConsultDate(String consultDate) {
        setConsultDate(consultDate);
        return this;
    }

}
