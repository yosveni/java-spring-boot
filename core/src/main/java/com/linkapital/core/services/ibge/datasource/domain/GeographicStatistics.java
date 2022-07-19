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
@Entity(name = "TAB_GEOGRAPHIC_STATISTICS")
@SequenceGenerator(name = "gen_geographic_statistics", sequenceName = "seq_geographic_statistics", allocationSize = 1)
public class GeographicStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_geographic_statistics")
    private Long id;

    @Column(name = "estimated_population_year")
    private int estimatedPopulationYear;

    @Column(name = "estimated_population_last_census_year")
    private int estimatedPopulationLastCensusYear;

    @Column(name = "demographic_density_year")
    private int demographicDensityYear;

    @Column(name = "estimated_population")
    private double estimatedPopulation;

    @Column(name = "estimated_population_last_census")
    private double estimatedPopulationLastCensus;

    @Column(name = "demographic_density")
    private double demographicDensity;

    public GeographicStatistics withEstimatedPopulationYear(int estimatedPopulationYear) {
        setEstimatedPopulationYear(estimatedPopulationYear);
        return this;
    }

    public GeographicStatistics withEstimatedPopulationLastCensusYear(int estimatedPopulationLastCensusYear) {
        setEstimatedPopulationLastCensusYear(estimatedPopulationLastCensusYear);
        return this;
    }

    public GeographicStatistics withDemographicDensityYear(int demographicDensityYear) {
        setDemographicDensityYear(demographicDensityYear);
        return this;
    }

    public GeographicStatistics withEstimatedPopulation(double estimatedPopulation) {
        setEstimatedPopulation(estimatedPopulation);
        return this;
    }

    public GeographicStatistics withEstimatedPopulationLastCensus(double estimatedPopulationLastCensus) {
        setEstimatedPopulationLastCensus(estimatedPopulationLastCensus);
        return this;
    }

    public GeographicStatistics withDemographicDensity(double demographicDensity) {
        setDemographicDensity(demographicDensity);
        return this;
    }

}
