package com.linkapital.core.services.bank_operation.datasource.domain;

import com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_BANK_OPERATION")
@SequenceGenerator(name = "gen_bank_operation", sequenceName = "gen_bank_operation", allocationSize = 1)
public class BankOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_bank_operation")
    private Long id;

    private String uf;

    private String product;

    private String type;

    @Column(name = "financial_cost")
    private String financialCost;

    @Column(name = "financial_agent")
    private String financialAgent;

    @Column(name = "grace_period")
    private int gracePeriod;

    @Column(name = "amortization_period")
    private int amortizationPeriod;

    private double tax;

    @Column(name = "contracted_value")
    private double contractedValue;

    @Column(name = "contracted_date")
    private Date contractedDate;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    private BankOperationStatus status;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public BankOperation withUf(String uf) {
        setUf(uf);
        return this;
    }

    public BankOperation withProduct(String product) {
        setProduct(product);
        return this;
    }

    public BankOperation withType(String type) {
        setType(type);
        return this;
    }

    public BankOperation withStatus(BankOperationStatus status) {
        setStatus(status);
        return this;
    }

    public BankOperation withFinancialCost(String financialCost) {
        setFinancialCost(financialCost);
        return this;
    }

    public BankOperation withFinancialAgent(String financialAgent) {
        setFinancialAgent(financialAgent);
        return this;
    }

    public BankOperation withGracePeriod(int gracePeriod) {
        setGracePeriod(gracePeriod);
        return this;
    }

    public BankOperation withAmortizationPeriod(int amortizationPeriod) {
        setAmortizationPeriod(amortizationPeriod);
        return this;
    }

    public BankOperation withTax(double tax) {
        setTax(tax);
        return this;
    }

    public BankOperation withContractedValue(double contractedValue) {
        setContractedValue(contractedValue);
        return this;
    }

    public BankOperation withContractedDate(Date contractedDate) {
        setContractedDate(contractedDate);
        return this;
    }

}
