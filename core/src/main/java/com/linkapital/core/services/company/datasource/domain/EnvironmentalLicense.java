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
@Entity(name = "TAB_ENVIRONMENTAL_LICENSE")
@SequenceGenerator(name = "gen_environmental_license", sequenceName = "seq_environmental_license", allocationSize = 1)
public class EnvironmentalLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_environmental_license")
    private Long id;

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "typology_number")
    private String typologyNumber;

    @Column(name = "description_typology", columnDefinition = "TEXT")
    private String descriptionTypology;

    private String situation;

    private String type;

    private String municipality;

    private String uf;

    @Column(name = "emit_data")
    private Date emitData;

    @Column(name = "update_data")
    private Date updateData;

}
