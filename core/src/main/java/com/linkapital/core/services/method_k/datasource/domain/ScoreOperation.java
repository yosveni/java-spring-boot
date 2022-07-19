package com.linkapital.core.services.method_k.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity(name = "TAB_SCORE_OPERATION")
@SequenceGenerator(name = "gen_score_operation", sequenceName = "seq_score_operation", allocationSize = 1)
public class ScoreOperation {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_score_operation")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private double value;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @PrePersist
    private void preSave() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }

    public ScoreOperation withDescription(String description) {
        setDescription(description);
        return this;
    }

    public ScoreOperation withValue(double value) {
        setValue(value);
        return this;
    }

}
