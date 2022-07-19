package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.company.contract.enums.EmitterType;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_CNDS")
@SequenceGenerator(name = "gen_cnds", sequenceName = "seq_cnds", allocationSize = 1)
public class Cnds {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnds")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String situation;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "emission_date")
    private LocalDate emissionDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "emitter_name")
    private EmitterType emitterName;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "document_id")
    private Directory document;

    public Cnds withSituation(String situation) {
        setSituation(situation);
        return this;
    }

    public Cnds withEmissionDate(LocalDate emissionDate) {
        setEmissionDate(emissionDate);
        return this;
    }

    public Cnds withExpirationDate(LocalDate expirationDate) {
        setExpirationDate(expirationDate);
        return this;
    }

    public Cnds withEmitterName(EmitterType emitterType) {
        setEmitterName(emitterType);
        return this;
    }

    public Cnds withDocument(Directory document) {
        setDocument(document);
        return this;
    }

}
