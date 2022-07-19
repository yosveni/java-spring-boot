package com.linkapital.core.services.person.datasource.domain;

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
@Entity(name = "TAB_RELATIONSHIP")
@SequenceGenerator(name = "gen_relationship", sequenceName = "seq_relationship", allocationSize = 1)
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_relationship")
    private Long id;

    private String cpf;

    private String name;

    private String description;

    public Relationship withCpf(String cpf) {
        setCpf(cpf);
        return this;
    }

    public Relationship withName(String name) {
        setName(name);
        return this;
    }

    public Relationship withDescription(String description) {
        setDescription(description);
        return this;
    }

}
