package com.linkapital.core.services.ibge.datasource.domain;

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
@Entity(name = "TAB_ECONOMIC_STATISTICS")
@SequenceGenerator(name = "gen_economic_statistics", sequenceName = "seq_economic_statistics", allocationSize = 1)
public class EconomicStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_economic_statistics")
    private Long id;

    @Column(name = "pib_year")
    private int pibYear;

    @Column(name = "idhm_year")
    private int idhmYear;

    @Column(name = "total_revenue_year")
    private int totalRevenueYear;

    @Column(name = "total_expenses_year")
    private int totalExpensesYear;

    @Column(name = "percentage_revenue_sources_year")
    private int percentageRevenueSourcesYear;

    private double pib;

    private double idhm;

    @Column(name = "total_revenue")
    private double totalRevenue;

    @Column(name = "total_expenses")
    private double totalExpenses;

    @Column(name = "percentage_revenue_sources")
    private double percentageRevenueSources;

    public EconomicStatistics withPibYear(int pibYear) {
        setPibYear(pibYear);
        return this;
    }

    public EconomicStatistics withIdhmYear(int idhmYear) {
        setIdhmYear(idhmYear);
        return this;
    }

    public EconomicStatistics withTotalRevenueYear(int totalRevenueYear) {
        setTotalRevenueYear(totalRevenueYear);
        return this;
    }

    public EconomicStatistics withTotalExpensesYear(int totalExpensesYear) {
        setTotalExpensesYear(totalExpensesYear);
        return this;
    }

    public EconomicStatistics withPercentageRevenueSourcesYear(int percentageRevenueSourcesYear) {
        setPercentageRevenueSourcesYear(percentageRevenueSourcesYear);
        return this;
    }

    public EconomicStatistics withPib(double pib) {
        setPib(pib);
        return this;
    }

    public EconomicStatistics withIdhm(double idhm) {
        setIdhm(idhm);
        return this;
    }

    public EconomicStatistics withTotalRevenue(double totalRevenue) {
        setTotalRevenue(totalRevenue);
        return this;
    }

    public EconomicStatistics withTotalExpenses(double totalExpenses) {
        setTotalExpenses(totalExpenses);
        return this;
    }

    public EconomicStatistics withPercentageRevenueSources(double percentageRevenueSources) {
        setPercentageRevenueSources(percentageRevenueSources);
        return this;
    }

}
