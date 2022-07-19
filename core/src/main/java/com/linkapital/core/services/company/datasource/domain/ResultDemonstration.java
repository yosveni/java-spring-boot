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

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_RESULT_DEMONSTRATION")
@SequenceGenerator(name = "gen_result_demonstration", sequenceName = "seq_result_demonstration", allocationSize = 1)
public class ResultDemonstration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_result_demonstration")
    private Long id;

    private int year;

    @Column(name = "benefit_period")
    private double benefitPeriod;

    @Column(name = "benefit_period_controller")
    private double benefitPeriodController;

    @Column(name = "recipe_sale")
    private double recipeSale;

    @Column(name = "recipe_brute")
    private double resultBrute;

    @Column(name = "result_patrimonial_equivalence")
    private double resultPatrimonialEquivalence;

    @Column(name = "result_financial")
    private double resultFinancial;

    @Column(name = "result_liquid_operations")
    private double resultLiquidOperations;

}
