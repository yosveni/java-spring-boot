package com.linkapital.core.services.bank_nomenclature.datasource.domain;

import com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

import static com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState.ANALYSIS;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity(name = "TAB_COMPANY_BANK_DOCUMENT")
@SequenceGenerator(name = "gen_company_bank_document", sequenceName = "seq_company_bank_document", allocationSize = 1)
public class CompanyBankDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_bank_document")
    private Long id;

    @Column(name = "description_state", columnDefinition = "TEXT")
    private String descriptionState;

    @Column(nullable = false)
    private CompanyBankDocumentState state;

    @OneToOne(cascade = MERGE)
    @JoinColumn(name = "bank_nomenclature_id", nullable = false)
    private BankNomenclature bankNomenclature;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_bank_document_id")
    private List<Directory> directories;

    public CompanyBankDocument() {
        this.state = ANALYSIS;
        this.directories = new ArrayList<>();
    }

    public CompanyBankDocument withDescriptionState(String descriptionState) {
        setDescriptionState(descriptionState);
        return this;
    }

    public CompanyBankDocument withState(CompanyBankDocumentState state) {
        setState(state);
        return this;
    }

    public CompanyBankDocument withBankNomenclature(BankNomenclature bankNomenclature) {
        setBankNomenclature(bankNomenclature);
        return this;
    }

    public CompanyBankDocument withDirectories(List<Directory> directory) {
        setDirectories(directory);
        return this;
    }

}
