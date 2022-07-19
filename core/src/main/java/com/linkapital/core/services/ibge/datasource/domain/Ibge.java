package com.linkapital.core.services.ibge.datasource.domain;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_IBGE")
@SequenceGenerator(name = "gen_ibge", sequenceName = "seq_ibge", allocationSize = 1)
public class Ibge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ibge")
    private Long id;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "geographic_statistics_id")
    private GeographicStatistics geographicStatistics;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "economic_statistics_id")
    private EconomicStatistics economicStatistics;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "work_performance_statistics_id")
    private WorkPerformanceStatistics workPerformanceStatistics;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public Ibge withGeographicStatistics(GeographicStatistics geographicStatistics) {
        setGeographicStatistics(geographicStatistics);
        return this;
    }

    public Ibge withEconomicStatistics(EconomicStatistics economicStatistics) {
        setEconomicStatistics(economicStatistics);
        return this;
    }

    public Ibge withWorkPerformanceStatistics(WorkPerformanceStatistics workPerformanceStatistics) {
        setWorkPerformanceStatistics(workPerformanceStatistics);
        return this;
    }

}
