package com.linkapital.core.services.commission.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMMISSION_PAYMENT_PERCENT")
@SequenceGenerator(name = "gen_commission_payment_percent", sequenceName = "seq_commission_payment_percent", allocationSize = 1)
public class CommissionPaymentPercent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_payment_percent")
    private Long id;

    private double disbursement;

    private double amortization;

    private double liquidation;

}
