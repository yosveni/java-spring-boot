package com.linkapital.core.services.directory.datasource.domain;

import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.person.datasource.domain.Person;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_DIRECTORY")
@SequenceGenerator(name = "gen_directory", sequenceName = "seq_directory", allocationSize = 1)
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_directory")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ext;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "company_debits_id")
    private CompanyUser companyDebits;

    @ManyToOne
    @JoinColumn(name = "company_nfe_duplicity_id")
    private CompanyUser companyNfeDuplicity;

    @ManyToOne
    @JoinColumn(name = "company_jucesp_id")
    private Company companyJucesp;

    @ManyToOne
    @JoinColumn(name = "person_irpf_id")
    private Person personIrpf;

    @ManyToOne
    @JoinColumn(name = "person_irpf_receipt_id")
    private Person personIrpfReceipt;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

}
