package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_EXPORT")
@SequenceGenerator(name = "gen_company_export", sequenceName = "seq_company_export", allocationSize = 1)
public class CompanyExport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_export")
    private Long id;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "company_import_id")
    private Company companyImport;

    @ManyToOne
    @JoinColumn(name = "company_export_id")
    private Company companyExport;

}
