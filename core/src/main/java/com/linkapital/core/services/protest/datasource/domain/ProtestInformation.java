package com.linkapital.core.services.protest.datasource.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity(name = "TAB_PROTEST_INFORMATION")
@SequenceGenerator(name = "gen_protest_information", sequenceName = "seq_protest_information", allocationSize = 1)
public class ProtestInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_protest_information")
    private Long id;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false, name = "total_error")
    private int totalError;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object analysis;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "protest_information_id")
    private Set<ProtestRegistry> protestRegistries;

    public ProtestInformation() {
        this.protestRegistries = new HashSet<>();
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

    public ProtestInformation withDocument(String document) {
        setDocument(document);
        return this;
    }

    public ProtestInformation withTotal(int total) {
        setTotal(total);
        return this;
    }

    public ProtestInformation withTotalError(int total) {
        setTotalError(total);
        return this;
    }

    public ProtestInformation withProtestRegistries(Set<ProtestRegistry> protestRegistries) {
        setProtestRegistries(protestRegistries);
        return this;
    }

    public ProtestInformation withAnalysis(Object analysis) {
        setAnalysis(analysis);
        return this;
    }

}
