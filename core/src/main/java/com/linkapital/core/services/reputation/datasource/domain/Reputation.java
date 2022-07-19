package com.linkapital.core.services.reputation.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_REPUTATION")
@SequenceGenerator(name = "gen_reputation", sequenceName = "seq_reputation", allocationSize = 1)
public class Reputation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_reputation")
    private Long id;

    @Column(name = "total_rating", nullable = false)
    private int totalRating;

    @Column(nullable = false)
    private int count1;

    @Column(nullable = false)
    private int count2;

    @Column(nullable = false)
    private int count3;

    @Column(nullable = false)
    private int count4;

    @Column(nullable = false)
    private int count5;

    @Column(nullable = false)
    private double avg;

    @Column(nullable = false)
    private double avg1;

    @Column(nullable = false)
    private double avg2;

    @Column(nullable = false)
    private double avg3;

    @Column(nullable = false)
    private double avg4;

    @Column(nullable = false)
    private double avg5;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "reputation_id")
    private List<Rating> ratings;

    public Reputation() {
        this.ratings = new ArrayList<>();
    }

}
