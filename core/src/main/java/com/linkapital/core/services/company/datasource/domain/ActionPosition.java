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
@Entity(name = "TAB_ACTION_POSITION")
@SequenceGenerator(name = "gen_action_position", sequenceName = "seq_action_position", allocationSize = 1)
public class ActionPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_action_position")
    private Long id;

    private String document;

    @Column(name = "common_actions_value")
    private double commonActionsValue;

    @Column(name = "preferential_actions_value")
    private double preferentialActionsValue;

    @Column(name = "total_value")
    private double totalValue;

}
