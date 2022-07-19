package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "TAB_IBAMA_CND")
@SequenceGenerator(name = "gen_ibama_cnd", sequenceName = "seq_ibama_cnd", allocationSize = 1)
public class IbamaCnd {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ibama_cnd")
    private Long id;

    @Column(name = "certificate_number")
    private String certificateNumber;

    private String situation;

    @Column(name = "emit_date")
    private LocalDate emitDate;

    @Column(name = "valid_date")
    private LocalDate validDate;

}
