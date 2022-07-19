package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_RELATED")
@SequenceGenerator(name = "gen_company_related", sequenceName = "seq_company_related", allocationSize = 1)
public class CompanyRelated {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_company_related")
    private Long id;

    private String cnpj;

    @Column(name = "social_reason", columnDefinition = "TEXT")
    private String socialReason;

    @Column(name = "description_cnae", columnDefinition = "TEXT")
    private String descriptionCnae;

    private String municipality;

    private String uf;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @PrePersist
    private void preSave() {
        this.created = now();
        this.modified = now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = now();
    }

    public CompanyRelated withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanyRelated withSocialReason(String socialReason) {
        setSocialReason(socialReason);
        return this;
    }

    public CompanyRelated withDescriptionCnae(String descriptionCnae) {
        setDescriptionCnae(descriptionCnae);
        return this;
    }

    public CompanyRelated withMunicipality(String municipality) {
        setMunicipality(municipality);
        return this;
    }

    public CompanyRelated withUf(String uf) {
        setUf(uf);
        return this;
    }

    public CompanyRelated withOpeningDate(LocalDateTime openingDate) {
        setOpeningDate(openingDate);
        return this;
    }

}
