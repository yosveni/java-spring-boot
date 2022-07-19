package com.linkapital.core.services.offer_installment.datasource.domain;

import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.offer.datasource.domain.Offer;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_OFFER_INSTALLMENT")
@SequenceGenerator(name = "gen_offer_installment", sequenceName = "seq_offer_installment", allocationSize = 1)
public class OfferInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_offer_installment")
    private Long id;

    @Column(nullable = false)
    private double total;

    @Column(name = "has_paid")
    private boolean hasPaid;

    @Column(nullable = false)
    private Date expiration;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "document_id")
    private Directory document;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public OfferInstallment withDocument(Directory document) {
        setDocument(document);
        return this;
    }

    public OfferInstallment withOffer(Offer offer) {
        setOffer(offer);
        return this;
    }

}
