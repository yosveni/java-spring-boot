package com.linkapital.core.services.bank_nomenclature.datasource.domain;

import com.linkapital.core.services.bank_nomenclature.contract.enums.BankDocumentStage;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Getter
@Setter
@Entity(name = "TAB_BANK_NOMENCLATURE")
@SequenceGenerator(name = "gen_bank_nomenclature", sequenceName = "seq_bank_nomenclature", allocationSize = 1)
public class BankNomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_bank_nomenclature")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private BankDocumentStage stage;

    @ElementCollection(targetClass = String.class)
    @Column(name = "ext", nullable = false)
    private Set<String> extensions;

    @OneToMany(mappedBy = "bankNomenclature", orphanRemoval = true, cascade = ALL)
    private Set<BankNomenclatureUrgency> nomenclatureUrgencies;

    @ManyToMany(mappedBy = "bankNomenclatures", cascade = {MERGE, REFRESH})
    private Set<PartnerBank> partnersBank;

    public BankNomenclature() {
        this.extensions = new HashSet<>();
        this.nomenclatureUrgencies = new HashSet<>();
        this.partnersBank = new HashSet<>();
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

    @PreRemove
    private void removePartnerFromBankNomenclature() {
        this.partnersBank.forEach(partnerBank -> partnerBank.getBankNomenclatures().remove(this));
        this.partnersBank.clear();
    }

    public BankNomenclature withPartnersBank(Collection<PartnerBank> partnersBank) {
        this.partnersBank.clear();
        this.partnersBank.addAll(partnersBank
                .stream()
                .map(partnerBank -> partnerBank.withBankNomenclature(this))
                .toList());
        return this;
    }

}
