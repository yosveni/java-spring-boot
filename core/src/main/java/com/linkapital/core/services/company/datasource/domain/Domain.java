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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_DOMAIN")
@SequenceGenerator(name = "gen_domain", sequenceName = "seq_domain", allocationSize = 1)
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_domain")
    private Long id;

    private String name;

    private String responsible;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
