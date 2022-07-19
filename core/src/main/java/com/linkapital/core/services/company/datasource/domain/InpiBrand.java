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
@Entity(name = "TAB_INPI_BRAND")
@SequenceGenerator(name = "gen_inpi_brand", sequenceName = "seq_inpi_brand", allocationSize = 1)
public class InpiBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_inpi_brand")
    private Long id;

    @Column(name = "class_brand")
    private String classBrand;

    @Column(name = "process_number")
    private String processNumber;

    private String situation;

    private String brand;

    @Column(name = "deposit_date")
    private Date depositDate;

}
