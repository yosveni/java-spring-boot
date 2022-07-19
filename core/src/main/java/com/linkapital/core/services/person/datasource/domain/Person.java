package com.linkapital.core.services.person.datasource.domain;

import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.services.shared.datasource.domain.Cafir;
import com.linkapital.core.services.shared.datasource.domain.DebitMte;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfnDau;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcess;
import com.linkapital.core.services.shared.datasource.domain.Property;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Entity(name = "TAB_PERSON")
@SequenceGenerator(name = "gen_person", sequenceName = "seq_person", allocationSize = 1)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_person")
    private Long id;

    @Column(length = 11, unique = true)
    private String cpf;

    private String name;

    private String sex;

    @Email
    private String email;

    private String cns;

    private String nis;

    @Column(name = "mother_cpf")
    private String motherCpf;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "social_inscription")
    private String socialInscription;

    @Column(name = "debt_pfgn_dau")
    private String debitPgfnDauValue;

    @Column(name = "situation_cpf")
    private String situationCpf;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "deficiency_type")
    private String deficiencyType;

    @Column(name = "profession_neoway")
    private String professionNeoway;

    @Column(name = "profession_cbo")
    private String professionCbo;

    private String situation;

    @Column(name = "register_situation")
    private String registerSituation;

    @Column(name = "marriage_regime")
    private String marriageRegime;

    @Column(name = "inscription_cpf_date")
    private String inscriptionCpfDate;

    @Column(name = "quantity_qsa_unique")
    private int quantityQsaUnique;

    private int age;

    @Column(name = "dead_date")
    private int deadDate;

    private boolean deficiency;

    private boolean dead;

    @Column(name = "dead_confirmation")
    private boolean deadConfirmation;

    @Column(name = "data_neo_way")
    private boolean dataNeoWay;

    @Column(name = "public_agent")
    private boolean publicAgent;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "main_address_id")
    private Address mainAddress;

    @OneToOne(orphanRemoval = true, cascade = PERSIST)
    @JoinColumn(name = "spouse_id")
    private Person spouse;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "cafir_id")
    private Cafir cafir;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "debit_pgfn_dau_id")
    private DebitPgfnDau debitPgfnDau;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "debit_mte_id")
    private DebitMte debitMte;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "judicial_process_id")
    private JudicialProcess judicialProcess;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "historical_criminal_id")
    private HistoricalCriminal historicalCriminal;

    @Column(name = "phone")
    @ElementCollection(targetClass = String.class)
    private Set<String> phones;

    @Column(name = "criminal_process")
    @ElementCollection(targetClass = String.class)
    private Set<String> criminalProcess;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<Address> otherAddresses;

    @OneToMany(mappedBy = "personIrpf", orphanRemoval = true, cascade = ALL)
    private List<Directory> irpfDocuments;

    @OneToMany(mappedBy = "personIrpfReceipt", orphanRemoval = true, cascade = ALL)
    private List<Directory> irpfReceiptDocuments;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<Irpf> irpf;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<HistoricalFunctional> historicalFunctional;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<Property> properties;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<Relationship> relationships;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<DisabilitiesBacen> disabilitiesBacens;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "person_id")
    private Set<CorporateParticipation> corporatesParticipation;

    public Person() {
        this.phones = new HashSet<>();
        this.criminalProcess = new HashSet<>();
        this.otherAddresses = new HashSet<>();
        this.irpfDocuments = new ArrayList<>();
        this.irpfReceiptDocuments = new ArrayList<>();
        this.irpf = new HashSet<>();
        this.historicalFunctional = new HashSet<>();
        this.properties = new HashSet<>();
        this.relationships = new HashSet<>();
        this.disabilitiesBacens = new HashSet<>();
        this.corporatesParticipation = new HashSet<>();
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

    public Person withCpf(String cpf) {
        setCpf(cpf);
        return this;
    }

    public Person withName(String name) {
        setName(name);
        return this;
    }

    public Person withEmail(String email) {
        setEmail(email);
        return this;
    }

}
