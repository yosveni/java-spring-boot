package com.linkapital.core.services.credit_information.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_EARNING")
@SequenceGenerator(name = "gen_earning", sequenceName = "seq_earning", allocationSize = 1)
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_earning")
    private Long id;

    private String code;

    private double value;

    public Earning withCode(String code) {
        setCode(code);
        return this;
    }

    public Earning withValue(double value) {
        setValue(value);
        return this;
    }

}
