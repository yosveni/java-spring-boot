package com.linkapital.core.services.bank_nomenclature.datasource.domain;

import com.linkapital.core.services.bank_nomenclature.contract.enums.BankDocumentUrgency;
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
@Entity(name = "TAB_BANK_NOMENCLATURE_URGENCY")
@SequenceGenerator(name = "gen_bank_nomenclature_urgency", sequenceName = "seq_bank_nomenclature_urgency", allocationSize = 1)
public class BankNomenclatureUrgency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_bank_nomenclature_urgency")
    private Long id;

    @Column(nullable = false)
    private int area;

    @Column(nullable = false)
    private BankDocumentUrgency urgency;

    @ManyToOne
    @JoinColumn(name = "bank_nomenclature_id", nullable = false)
    private BankNomenclature bankNomenclature;

    public BankNomenclatureUrgency withBankNomenclature(BankNomenclature bankNomenclature) {
        setBankNomenclature(bankNomenclature);
        return this;
    }

}
