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
@Entity(name = "TAB_CEPIM")
@SequenceGenerator(name = "gen_cepim", sequenceName = "seq_cepim", allocationSize = 1)
public class Cepim {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cepim")
    private Long id;

    @Column(name = "grantor_entity")
    private String grantorEntity;

    private String impediment;

    private String contract;

    @Column(name = "value_released")
    private double valueReleased;

    @Column(name = "end_contract_date")
    private Date endContractDate;

}
