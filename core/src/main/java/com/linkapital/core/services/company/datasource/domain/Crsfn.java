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
@Entity(name = "TAB_CRSFN")
@SequenceGenerator(name = "gen_crsfn", sequenceName = "seq_crsfn", allocationSize = 1)
public class Crsfn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_crsfn")
    private Long id;

    @Column(name = "agreed_number")
    private String agreedNumber;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "resource_number")
    private String resourceNumber;

    private String part;

    @Column(name = "resource_type")
    private String resourceType;

}
