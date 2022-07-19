package com.linkapital.core.services.company.datasource.domain;

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
@Entity(name = "TAB_MEAL_PROVIDED")
@SequenceGenerator(name = "gen_meal_provided", sequenceName = "seq_meal_provided", allocationSize = 1)
public class MealProvided {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_meal_provided")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int quantity;

}
