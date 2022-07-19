package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_PENALTY")
@SequenceGenerator(name = "gen_penalty", sequenceName = "seq_penalty", allocationSize = 1)
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_penalty")
    private Long id;

    private String reason;

    private Date crated;

}
