package com.linkapital.core.services.reputation.datasource.domain;

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
@Entity(name = "TAB_RATING")
@SequenceGenerator(name = "gen_rating", sequenceName = "seq_rating", allocationSize = 1)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_rating")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "rating_value", nullable = false)
    private int ratingValue;

}
