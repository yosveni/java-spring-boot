package com.linkapital.core.services.company.datasource.domain;

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
@Entity(name = "TAB_FINANCIAL_INDICATOR")
@SequenceGenerator(name = "gen_financial_indicator", sequenceName = "seq_financial_indicator", allocationSize = 1)
public class FinancialIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_financial_indicator")
    private Long id;

    private double margin;

    private double increase;

}
