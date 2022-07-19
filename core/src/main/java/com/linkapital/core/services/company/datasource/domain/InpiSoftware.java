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
@Entity(name = "TAB_INPI_SOFTWARE")
@SequenceGenerator(name = "gen_inpi_software", sequenceName = "seq_inpi_software", allocationSize = 1)
public class InpiSoftware {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_inpi_software")
    private Long id;

    @Column(name = "process_number")
    private String processNumber;

    private String procurator;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(name = "deposit_date")
    private Date depositDate;

    @Column(name = "author")
    @ElementCollection(targetClass = String.class)
    private List<String> authors;

    public InpiSoftware() {
        this.authors = new ArrayList<>();
    }

}
