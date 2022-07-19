package com.linkapital.core.services.industrial_production_index.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_PHYSICAL_PRODUCTION")
@SequenceGenerator(name = "gen_physical_production", sequenceName = "seq_physical_production", allocationSize = 1)
public class PhysicalProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_physical_production")
    private Long id;

    @Column(name = "territorial_Level")
    private String territorialLevel;

    @Column(name = "code_description", columnDefinition = "TEXT")
    private String codeDescription;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "monthly_index_id", nullable = false)
    private PhysicalProductionVariable monthlyIndex;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "fixed_base_index_without_seasonal_adjustment_id", nullable = false)
    private PhysicalProductionVariable fixedBaseIndexWithoutSeasonalAdjustment;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "index_accumulated_last_12_months_id", nullable = false)
    private PhysicalProductionVariable indexAccumulatedLast12Months;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "monthly_percentage_change_id", nullable = false)
    private PhysicalProductionVariable monthlyPercentageChange;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "percentage_change_accumulated_year_id", nullable = false)
    private PhysicalProductionVariable percentageChangeAccumulatedYear;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "year_to_date_index_id", nullable = false)
    private PhysicalProductionVariable yearToDateIndex;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "percentage_change_accumulated_last_12_months_id", nullable = false)
    private PhysicalProductionVariable percentageChangeAccumulatedLast12Months;

}
