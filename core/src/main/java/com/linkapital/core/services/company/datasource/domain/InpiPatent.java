package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_INPI_PATENT")
@SequenceGenerator(name = "gen_inpi_patent", sequenceName = "seq_inpi_patent", allocationSize = 1)
public class InpiPatent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_inpi_patent")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String depositor;

    @Column(columnDefinition = "TEXT")
    private String procurator;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "concession_date")
    private Date concessionDate;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "deposit_date")
    private Date depositDate;

    @Column(name = "inventor")
    @ElementCollection(targetClass = String.class)
    private List<String> inventors;

    public InpiPatent() {
        this.inventors = new ArrayList<>();
    }

}
