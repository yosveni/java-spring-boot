package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_CNJCNIA")
@SequenceGenerator(name = "gen_cnjcnia", sequenceName = "seq_cnjcnia", allocationSize = 1)
public class CnjCnia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnjcnia")
    private Long id;

    private String uf;

    private String sphere;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "description_entity")
    private String descriptionEntity;

    private double value;

    @Column(name = "registration_date")
    private Date registrationDate;

    @ElementCollection(targetClass = String.class)
    @JoinTable(name = "TAB_CNJCNIA_RELATED_ISSUES")
    private List<String> relatedIssues;

    public CnjCnia() {
        this.relatedIssues = new ArrayList<>();
    }

}
