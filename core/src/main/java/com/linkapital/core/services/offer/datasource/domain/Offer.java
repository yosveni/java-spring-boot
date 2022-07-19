package com.linkapital.core.services.offer.datasource.domain;

import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.commission.datasource.domain.Commission;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.offer.contract.enums.OfferState;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.linkapital.core.services.offer.contract.enums.OfferState.READY;
import static java.util.stream.Collectors.toSet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity(name = "TAB_OFFER")
@SequenceGenerator(name = "gen_offer", sequenceName = "seq_offer", allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_offer")
    private Long id;

    @Column(nullable = false)
    private String cnpj;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "next_step_description", columnDefinition = "TEXT")
    private String nextStepDescription;

    @Column(name = "rejected_reason", columnDefinition = "TEXT")
    private String rejectedReason;

    @Column(name = "month_cet")
    private int monthCet;

    @Column(name = "year_cet")
    private int yearCet;

    @Column(insertable = false, updatable = false)
    private int type;

    private double volume;

    private double installments;

    @Column(name = "tax_percent")
    private double taxPercent;

    @Column(name = "tax_value")
    private double taxValue;

    private double discount;

    private double iof;

    @Column(name = "registration_fee")
    private double registrationFee;

    private double total;

    @Column(name = "pay_by_installment")
    private double payByInstallment;

    private boolean accepted;

    @Column(name = "response_time")
    private Date responseTime;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "contract_date")
    private Date contractDate;

    @Column(nullable = false)
    private OfferState state;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "image_id")
    private Directory image;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "commission_id")
    private Commission commission;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "partner_bank_id")
    private PartnerBank partnerBank;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "indicative_offer_id", nullable = false)
    private IndicativeOffer indicativeOffer;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "offer_id")
    @OrderBy
    private Set<OfferStateLogs> offerStateLogs;

    @OneToMany(mappedBy = "offer", orphanRemoval = true, cascade = ALL)
    @OrderBy("expiration")
    private Set<OfferInstallment> payments;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "offer_id")
    @OrderBy
    private Set<Comment> comments;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "offer_id")
    private Set<Directory> documents;

    public Offer() {
        this.state = READY;
        this.offerStateLogs = new HashSet<>();
        this.payments = new HashSet<>();
        this.comments = new HashSet<>();
        this.documents = new HashSet<>();
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

    public Offer withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public Offer withDescription(String description) {
        setDescription(description);
        return this;
    }

    public Offer withNextStepDescription(String nextStepDescription) {
        setNextStepDescription(nextStepDescription);
        return this;
    }

    public Offer withRejectedReason(String rejectedReason) {
        setRejectedReason(rejectedReason);
        return this;
    }

    public Offer withType(int type) {
        setType(type);
        return this;
    }

    public Offer withYearCet(int yearCet) {
        setYearCet(yearCet);
        return this;
    }

    public Offer withMonthCet(int monthCet) {
        setMonthCet(monthCet);
        return this;
    }

    public Offer withVolume(double volume) {
        setVolume(volume);
        return this;
    }

    public Offer withInstallments(double installments) {
        setInstallments(installments);
        return this;
    }

    public Offer withTaxValue(double taxValue) {
        setTaxValue(taxValue);
        return this;
    }

    public Offer withDiscount(double discount) {
        setDiscount(discount);
        return this;
    }

    public Offer withTaxPercent(double taxPercent) {
        setTaxPercent(taxPercent);
        return this;
    }

    public Offer withIof(double iof) {
        setIof(iof);
        return this;
    }

    public Offer withRegistrationFee(double registrationFee) {
        setRegistrationFee(registrationFee);
        return this;
    }

    public Offer withTotal(double total) {
        setTotal(total);
        return this;
    }

    public Offer withPayByInstallment(double paiByInstallment) {
        setPayByInstallment(paiByInstallment);
        return this;
    }

    public Offer withAccepted(boolean accepted) {
        setAccepted(accepted);
        return this;
    }

    public Offer withResponseTime(Date responseTime) {
        setResponseTime(responseTime);
        return this;
    }

    public Offer withContractDate(Date contractDate) {
        setContractDate(contractDate);
        return this;
    }

    public Offer withState(OfferState offerState) {
        setState(offerState);
        return this;
    }

    public Offer withPartnerBank(PartnerBank partnerBank) {
        setPartnerBank(partnerBank);
        return this;
    }

    public Offer withComments(Comment comments) {
        this.comments.add(comments);
        return this;
    }

    public Offer withDocuments(Collection<Directory> documents) {
        this.documents.clear();
        this.documents.addAll(documents);
        return this;
    }

    public Offer withIndicativeOffer(IndicativeOffer indicativeOffer) {
        setIndicativeOffer(indicativeOffer);
        return this;
    }

    public Offer withPayments(Set<OfferInstallment> payments) {
        getPayments().addAll(payments
                .stream()
                .map(offerInstallment -> offerInstallment.withOffer(this))
                .collect(toSet()));
        return this;
    }

    public Offer withUser(User user) {
        setUser(user);
        return this;
    }

}
