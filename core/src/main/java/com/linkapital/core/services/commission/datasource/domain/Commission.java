package com.linkapital.core.services.commission.datasource.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity(name = "TAB_COMMISSION")
@SequenceGenerator(name = "gen_commission", sequenceName = "seq_commission", allocationSize = 1)
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission")
    private Long id;

    private double total;

    private double disbursement;

    private double amortization;

    private double liquidation;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private Object campaign;

    @OneToMany(mappedBy = "commission", orphanRemoval = true, cascade = ALL)
    @OrderBy
    private Set<CommissionInstallment> installments;

    public Commission() {
        this.installments = new HashSet<>();
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

    public Commission withTotal(double total) {
        setTotal(total);
        return this;
    }

    public Commission withDisbursement(double disbursement) {
        setDisbursement(disbursement);
        return this;
    }

    public Commission withAmortization(double amortization) {
        setAmortization(amortization);
        return this;
    }

    public Commission withLiquidation(double liquidation) {
        setLiquidation(liquidation);
        return this;
    }

    public Commission withReleaseDate(Date releaseDate) {
        setReleaseDate(releaseDate);
        return this;
    }

    public Commission withCampaign(Object campaign) {
        setCampaign(campaign);
        return this;
    }

    public Commission withInstallments(Set<CommissionInstallment> installments) {
        setInstallments(installments);
        return this;
    }

}
