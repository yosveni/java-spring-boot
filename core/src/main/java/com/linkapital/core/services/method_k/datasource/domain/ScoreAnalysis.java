package com.linkapital.core.services.method_k.datasource.domain;

import com.linkapital.core.services.method_k.contract.enums.ScoreType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "TAB_SCORE_ANALYSIS")
@SequenceGenerator(name = "gen_score_analysis", sequenceName = "seq_score_analysis", allocationSize = 1)
public class ScoreAnalysis {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_score_analysis")
    private Long id;

    private int year;

    private double total;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @Column(nullable = false)
    private ScoreType type;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "score_analysis_id")
    private Set<ScoreOperation> operations;

    public ScoreAnalysis() {
        this.operations = new HashSet<>();
    }

    @PrePersist
    private void preSave() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }

    public ScoreAnalysis withYear(int year) {
        setYear(year);
        return this;
    }

    public ScoreAnalysis withTotal(double total) {
        setTotal(total);
        return this;
    }

    public ScoreAnalysis withType(ScoreType type) {
        setType(type);
        return this;
    }

    public ScoreAnalysis withOperations(Set<ScoreOperation> operations) {
        setOperations(operations);
        return this;
    }

}
