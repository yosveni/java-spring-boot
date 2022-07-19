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
@Entity(name = "TAB_CONTRACT")
@SequenceGenerator(name = "gen_contract", sequenceName = "seq_contract", allocationSize = 1)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_contract")
    private Long id;

    @Column(name = "contract_number")
    private String contractNumber;

    private String sphere;

    private String organ;

    private String uf;

    @Column(name = "months_validity")
    private int monthsValidity;

    @Column(name = "final_value")
    private double finalValue;

    @Column(name = "init_date")
    private Date initDate;

    @Column(name = "end_date")
    private Date endDate;

}
