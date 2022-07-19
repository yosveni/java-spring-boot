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
@Entity(name = "TAB_INTERNATIONAL_LIST")
@SequenceGenerator(name = "gen_international_list", sequenceName = "seq_international_list", allocationSize = 1)
public class InternationalList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_international_list")
    private Long id;

    private String name;

    @Column(name = "query_date")
    private Date queryDate;

}
