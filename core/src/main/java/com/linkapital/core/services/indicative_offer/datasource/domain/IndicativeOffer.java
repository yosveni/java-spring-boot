package com.linkapital.core.services.indicative_offer.datasource.domain;

import com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState;
import com.linkapital.core.services.offer.datasource.domain.Offer;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.WITHOUT_OFFER;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity(name = "TAB_INDICATIVE_OFFER")
@SequenceGenerator(name = "gen_indicative_offer", sequenceName = "seq_indicative_offer", allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public class IndicativeOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_indicative_offer")
    private Long id;

    @Column(name = "dead_line")
    private String deadline;

    @Column(insertable = false, updatable = false)
    private int type;

    @Column(nullable = false)
    private double volume;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private double precision;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private IndicativeOfferState state;

    @OneToMany(mappedBy = "indicativeOffer", orphanRemoval = true, fetch = EAGER, cascade = ALL)
    @OrderBy
    private Set<Offer> offers;

    public IndicativeOffer() {
        this.state = WITHOUT_OFFER;
        this.offers = new HashSet<>();
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

    public IndicativeOffer withDeadline(String deadline) {
        setDeadline(deadline);
        return this;
    }

    public IndicativeOffer withVolume(double volume) {
        setVolume(volume);
        return this;
    }

    public IndicativeOffer withTax(double tax) {
        setTax(tax);
        return this;
    }

    public IndicativeOffer withPrecision(double precision) {
        setPrecision(precision);
        return this;
    }

    public IndicativeOffer withIndicativeOfferState(IndicativeOfferState indicativeOfferState) {
        setState(indicativeOfferState);
        return this;
    }

}
