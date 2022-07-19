package com.linkapital.core.services.company_user.datasource.domain;

import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.comment.contract.enums.LearningSession;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.company.contract.enums.CompanyState;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.LearningAnalysis;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.interview.datasource.domain.AnswerInterview;
import com.linkapital.core.services.invoice.datasource.domain.Invoice;
import com.linkapital.core.services.method_k.datasource.domain.ScoreAnalysis;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.linkapital.core.services.company.contract.enums.CompanyState.READY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity(name = "TAB_COMPANY_USER")
@SequenceGenerator(name = "gen_company_user", sequenceName = "seq_company_user", allocationSize = 1)
public class CompanyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_user")
    private Long id;

    @Column(name = "avg_receipt_term_invoices")
    private int avgReceiptTermInvoices;

    @Column(name = "credit_requested")
    private double creditRequested;

    @Column(name = "invoicing_informed")
    private double invoicingInformed;

    @Column(name = "init_indictive_offer_one")
    private boolean initIndicativeOfferOne;

    @Column(name = "init_indictive_offer_two")
    private boolean initIndicativeOfferTwo;

    @Column(name = "init_indicative_offer_three")
    private boolean initIndicativeOfferThree;

    @Column(name = "init_indicative_offer_four")
    private boolean initIndicativeOfferFour;

    @Column(name = "latest_registration_form")
    private boolean latestRegistrationForm;

    private boolean owner;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "company_state", nullable = false)
    private CompanyState companyState;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "indicative_offer_one")
    private IndicativeOffer indicativeOfferOne;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "indicative_offer_two")
    private IndicativeOffer indicativeOfferTwo;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "indicative_offer_three")
    private IndicativeOffer indicativeOfferThree;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "indicative_offer_four")
    private IndicativeOffer indicativeOfferFour;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "owner_authorization_id")
    private OwnerAuthorization ownerAuthorization;

    @ManyToOne(cascade = {MERGE, REFRESH})
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToOne(cascade = {MERGE, REFRESH})
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "learning_analysis")
    private LearningAnalysis learningAnalysis;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "sped_document_id")
    private Directory spedDocument;

    @ElementCollection(targetClass = LearningSession.class)
    private Set<LearningSession> learningSessions;

    @OneToMany(orphanRemoval = true, fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "company_user_id")
    private Set<CompanyBankDocument> bankDocuments;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_user_id")
    private Set<AnswerInterview> answerInterviews;

    @OneToMany(mappedBy = "companyUser", orphanRemoval = true, cascade = ALL)
    @OrderBy
    private Set<Comment> comments;

    @OneToMany(mappedBy = "companyDebits", orphanRemoval = true, cascade = ALL)
    private Set<Directory> debtDocuments;

    @OneToMany(mappedBy = "companyNfeDuplicity", orphanRemoval = true, cascade = ALL)
    private Set<Directory> nfeDuplicity;

    @OneToMany(mappedBy = "companyInvoice", orphanRemoval = true, cascade = ALL)
    private Set<Invoice> issuedInvoices;

    @OneToMany(mappedBy = "companyReceived", orphanRemoval = true, cascade = ALL)
    private Set<Invoice> receivedInvoices;

    @OneToMany(mappedBy = "companyUser", orphanRemoval = true, cascade = ALL)
    private Set<PropertyGuarantee> propertyGuarantees;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_user_id")
    private Set<ScoreAnalysis> scoreAnalysis;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_user_id")
    private List<Sped> speds;

    public CompanyUser() {
        this.companyState = READY;
        this.answerInterviews = new HashSet<>();
        this.comments = new HashSet<>();
        this.learningSessions = new HashSet<>();
        this.debtDocuments = new HashSet<>();
        this.bankDocuments = new HashSet<>();
        this.issuedInvoices = new HashSet<>();
        this.receivedInvoices = new HashSet<>();
        this.propertyGuarantees = new HashSet<>();
        this.nfeDuplicity = new HashSet<>();
        this.scoreAnalysis = new HashSet<>();
        this.speds = new ArrayList<>();
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

    public CompanyUser withCompany(Company company) {
        setCompany(company);
        return this;
    }

    public CompanyUser withUser(User user) {
        setUser(user);
        return this;
    }

    public CompanyUser withOwnerAuthorization(OwnerAuthorization ownerAuthorization) {
        setOwnerAuthorization(ownerAuthorization);
        return this;
    }

    public void withNfeDuplicates(List<Directory> nfeDuplicates) {
        this.nfeDuplicity.clear();
        this.nfeDuplicity.addAll(nfeDuplicates);
    }

    public CompanyUser withInitIndicativeOfferOne(boolean initIndicativeOfferOne) {
        setInitIndicativeOfferOne(initIndicativeOfferOne);
        return this;
    }

    public CompanyUser withInitIndicativeOfferTwo(boolean initIndicativeOfferTwo) {
        setInitIndicativeOfferTwo(initIndicativeOfferTwo);
        return this;
    }

    public CompanyUser withInitIndicativeOfferThree(boolean initIndicativeOfferThree) {
        setInitIndicativeOfferThree(initIndicativeOfferThree);
        return this;
    }

    public CompanyUser withInitIndicativeOfferFour(boolean initIndicativeOfferFour) {
        setInitIndicativeOfferFour(initIndicativeOfferFour);
        return this;
    }

    public CompanyUser withCreditRequested(double creditRequested) {
        setCreditRequested(creditRequested);
        return this;
    }

    public CompanyUser withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    public CompanyUser withAvgReceiptTermInvoices(int avgReceiptTermInvoices) {
        setAvgReceiptTermInvoices(avgReceiptTermInvoices);
        return this;
    }

    public CompanyUser withSpeds(List<Sped> speds, boolean clean) {
        if (clean)
            this.speds.clear();
        this.speds.addAll(speds);

        return this;
    }

}
