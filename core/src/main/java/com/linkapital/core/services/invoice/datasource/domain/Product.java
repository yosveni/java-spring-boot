package com.linkapital.core.services.invoice.datasource.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_PRODUCT")
@SequenceGenerator(name = "gen_product", sequenceName = "seq_product", allocationSize = 1)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_product")
    private Long id;

    private Long cfop;

    private String code;

    private String description;

    private String ncm;

    @Column(name = "cean")
    private String cEan;

    private String cest;

    @Column(name = "ucom")
    private String uCom;

    @Column(name = "c_ean_trib")
    private String cEanTrib;

    @Column(name = "u_trib")
    private String uTrib;

    @Column(name = "ind_tot")
    private String indTot;

    private double count;

    private double price;

    @Column(name = "v_un_com")
    private double vUnCom;

    @Column(name = "v_prod")
    private double vProd;

    @Column(name = "q_trib")
    private double qTrib;


}
