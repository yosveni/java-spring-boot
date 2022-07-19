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
@Entity(name = "TAB_INDIRECT_TAX")
@SequenceGenerator(name = "gen_indirect_tax", sequenceName = "seq_indirect_tax", allocationSize = 1)
public class IndirectTax {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_indirect_tax")
    private Long id;

    private double ipi;

    private double pis;

    private double cofins;

    private double icms;

    @Column(name = "v_bc")
    private double vBc;

    @Column(name = "v_icms_deson")
    private double vIcmsDeson;

    @Column(name = "v_fcpuf_dest")
    private double vFcpufDest;

    @Column(name = "v_icmsuf_dest")
    private double vIcmsufDest;

    @Column(name = "v_icmsuf_remet")
    private double vIcmsufRemet;

    @Column(name = "v_fcp")
    private double vFcp;

    @Column(name = "v_bcst")
    private double vBcst;

    @Column(name = "v_st")
    private double vSt;

    @Column(name = "v_fcpst")
    private double vFcpst;

    @Column(name = "v_fcpst_ret")
    private double vFcpstRet;

    @Column(name = "v_prod")
    private double vProd;

    @Column(name = "v_frete")
    private double vFrete;

    @Column(name = "v_seg")
    private double vSeg;

    @Column(name = "v_desc")
    private double vDesc;

    @Column(name = "v_ii")
    private double v_Ii;

    @Column(name = "v_Ipi_devol")
    private double vIpiDevol;

    @Column(name = "v_outro")
    private double vOutro;

    @Column(name = "v_nf")
    private double vNf;


}
