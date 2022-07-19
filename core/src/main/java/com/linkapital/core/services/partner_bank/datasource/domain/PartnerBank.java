package com.linkapital.core.services.partner_bank.datasource.domain;

import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity(name = "TAB_PARTNER_BANK")
@SequenceGenerator(name = "gen_partner_bank", sequenceName = "seq_partner_bank", allocationSize = 1)
public class PartnerBank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_partner_bank")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int days;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @ElementCollection(targetClass = Integer.class, fetch = EAGER)
    @Column(name = "area", nullable = false)
    private Set<Integer> areas;

    @ManyToMany(cascade = {MERGE, REFRESH})
    @JoinTable(name = "tab_bank_nomenclature_partners_bank",
            joinColumns = @JoinColumn(name = "partner_bank_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_nomenclature_id"))
    private Set<BankNomenclature> bankNomenclatures;

    public PartnerBank() {
        this.areas = new HashSet<>();
        this.bankNomenclatures = new HashSet<>();
    }

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public PartnerBank withBankNomenclature(BankNomenclature bankNomenclature) {
        this.bankNomenclatures.add(bankNomenclature);
        return this;
    }

    public PartnerBank withBankNomenclatures(Collection<BankNomenclature> bankNomenclatures) {
        this.bankNomenclatures.clear();
        this.bankNomenclatures.addAll(bankNomenclatures);
        return this;
    }

}
