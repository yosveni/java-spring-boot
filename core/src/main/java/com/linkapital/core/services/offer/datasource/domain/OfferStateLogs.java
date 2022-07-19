package com.linkapital.core.services.offer.datasource.domain;

import com.linkapital.core.services.offer.contract.enums.OfferState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_OFFER_STATE_LOGS")
@SequenceGenerator(name = "gen_offer_state_logs", sequenceName = "seq_offer_state_logs", allocationSize = 1)
public class OfferStateLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_offer_state_logs")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String notification;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "offer_state", nullable = false)
    private OfferState offerState;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public OfferStateLogs withNotification(String notification) {
        setNotification(notification);
        return this;
    }

    public OfferStateLogs withOfferState(OfferState offerState) {
        setOfferState(offerState);
        return this;
    }

}
