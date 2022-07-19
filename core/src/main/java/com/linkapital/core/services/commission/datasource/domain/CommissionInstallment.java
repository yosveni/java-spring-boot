package com.linkapital.core.services.commission.datasource.domain;

import com.linkapital.core.services.commission.contract.enums.InstallmentType;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMMISSION_INSTALLMENT")
@SequenceGenerator(name = "gen_commission_installment", sequenceName = "seq_commission_installment", allocationSize = 1)
public class CommissionInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_installment")
    private Long id;

    private double total;

    @Column(name = "total_base")
    private double totalBase;

    @Column(name = "has_paid")
    private boolean hasPaid;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "document_id")
    private Directory document;

    @ManyToOne
    @JoinColumn(name = "commission_id", nullable = false)
    private Commission commission;

    @Column(name = "installment_type", nullable = false)
    private InstallmentType type;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public CommissionInstallment withTotal(double total) {
        setTotal(total);
        return this;
    }

    public CommissionInstallment withTotalBase(double totalBase) {
        setTotalBase(totalBase);
        return this;
    }

    public CommissionInstallment withHasPaid(boolean hasPaid) {
        setHasPaid(hasPaid);
        return this;
    }

    public CommissionInstallment withPaymentDate(Date paymentDate) {
        setPaymentDate(paymentDate);
        return this;
    }

    public CommissionInstallment withInstallmentType(InstallmentType installmentType) {
        setType(installmentType);
        return this;
    }

    public CommissionInstallment withCommission(Commission commission) {
        setCommission(commission);
        return this;
    }

    public CommissionInstallment withDocument(Directory cocument) {
        setDocument(document);
        return this;
    }

}
