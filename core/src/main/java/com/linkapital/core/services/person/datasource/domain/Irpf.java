package com.linkapital.core.services.person.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_IRPF")
@SequenceGenerator(name = "gen_irpf", sequenceName = "seq_irpf", allocationSize = 1)
public class Irpf {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_irpf")
    private Long id;

    private String bank;

    private String agency;

    private String lot;

    @Column(name = "statement_status")
    private String statementStatus;

    @Column(name = "year_exercise")
    private int yearExercise;

    @Column(name = "availability_date")
    private Date availabilityDate;

}
