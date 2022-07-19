package com.linkapital.core.services.commission.datasource.domain;

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
@Entity(name = "TAB_COMMISSION_PERCENT")
@SequenceGenerator(name = "gen_commission_percent", sequenceName = "seq_commission_percent", allocationSize = 1)
public class CommissionPercent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_percent")
    private Long id;

    @Column(name = "month_min")
    private int monthMin;

    @Column(name = "month_max")
    private int monthMax;

    private double percent;

}
