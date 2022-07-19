package com.linkapital.core.services.commission.datasource.domain;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "TAB_COMMISSION_CAMPAIGN")
@SequenceGenerator(name = "gen_commission_campaign", sequenceName = "seq_commission_campaign", allocationSize = 1)
public class CommissionCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_campaign")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    private boolean base;

    private boolean active;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "limit_id")
    private CommissionCampaignLimit limit;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "payment_percent_id", nullable = false)
    private CommissionPaymentPercent paymentPercent;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "commission_campaign_id")
    private List<CommissionPercent> percents;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "commission_campaign_id")
    private List<CommissionCampaignCondition> conditions;

    @OneToMany(mappedBy = "campaign", cascade = ALL)
    private Set<CommissionCampaignUser> campaignUsers;

    public CommissionCampaign() {
        this.percents = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.campaignUsers = new HashSet<>();
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

}
