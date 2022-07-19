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
@Entity(name = "TAB_WORK_PERFORMANCE_STATISTICS")
@SequenceGenerator(name = "gen_work_performance_statistics", sequenceName = "seq_work_performance_statistics",
        allocationSize = 1)
public class WorkPerformanceStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_work_performance_statistics")
    private Long id;

    @Column(name = "average_salary_year")
    private int averageSalaryYear;

    @Column(name = "busy_people_year")
    private int busyPeopleYear;

    @Column(name = "occupied_population_year")
    private int occupiedPopulationYear;

    @Column(name = "population_income_Monthly_nominal_year")
    private int populationIncomeMonthlyNominalYear;

    @Column(name = "average_salary")
    private double averageSalary;

    @Column(name = "busy_people")
    private double busyPeople;

    @Column(name = "occupied_population")
    private double occupiedPopulation;

    @Column(name = "population_income_Monthly_nominal")
    private double populationIncomeMonthlyNominal;

    public WorkPerformanceStatistics withAverageSalaryYear(int averageSalaryYear) {
        setAverageSalaryYear(averageSalaryYear);
        return this;
    }

    public WorkPerformanceStatistics withBusyPeopleYear(int busyPeopleYear) {
        setBusyPeopleYear(busyPeopleYear);
        return this;
    }

    public WorkPerformanceStatistics withOccupiedPopulationYear(int occupiedPopulationYear) {
        setOccupiedPopulationYear(occupiedPopulationYear);
        return this;
    }

    public WorkPerformanceStatistics withPopulationIncomeMonthlyNominalYear(int populationIncomeMonthlyNominalYear) {
        setPopulationIncomeMonthlyNominalYear(populationIncomeMonthlyNominalYear);
        return this;
    }

    public WorkPerformanceStatistics withAverageSalary(double averageSalary) {
        setAverageSalary(averageSalary);
        return this;
    }

    public WorkPerformanceStatistics withBusyPeople(double busyPeople) {
        setBusyPeople(busyPeople);
        return this;
    }

    public WorkPerformanceStatistics withOccupiedPopulation(double occupiedPopulation) {
        setOccupiedPopulation(occupiedPopulation);
        return this;
    }

    public WorkPerformanceStatistics withPopulationIncomeMonthlyNominal(double populationIncomeMonthlyNominal) {
        setPopulationIncomeMonthlyNominal(populationIncomeMonthlyNominal);
        return this;
    }

}
