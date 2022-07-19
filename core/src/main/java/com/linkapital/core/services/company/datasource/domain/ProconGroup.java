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
@Entity(name = "TAB_PROCON_GROUP")
@SequenceGenerator(name = "gen_procon_group", sequenceName = "seq_procon_group", allocationSize = 1)
public class ProconGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_procon_group")
    private Long id;

    private String cnpj;

    @Column(name = "total_penalty_value")
    private double totalPenaltyValue;

}
