package com.linkapital.core.services.company.datasource.domain;

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
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_PARTNERS")
@SequenceGenerator(name = "gen_company_partners", sequenceName = "seq_company_partners", allocationSize = 1)
public class CompanyPartners {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_partners")
    private Long id;

    private String qualification;

    @Column(name = "qualification_rf")
    private String qualificationRF;

    @Column(name = "level_preparation")
    private String levelPreparation;

    @Column(name = "level_preparation_rf")
    private String levelPreparationRF;

    private double participation;

    @Column(name = "participation_rf")
    private double participationRF;

    @Column(name = "participation_social_capital")
    private double participationSocialCapital;

    @Column(name = "participation_social_capital_rf")
    private double participationSocialCapitalRF;

    @Column(name = "entry_date")
    private Date entryDate;

    @ManyToOne
    @JoinColumn
    private Person person;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn
    private Company companyPartner;

    public CompanyPartners withPerson(Person person) {
        setPerson(person);
        return this;
    }

    public CompanyPartners withCompanyPartner(Company companyPartner) {
        setCompanyPartner(companyPartner);
        return this;
    }

}
