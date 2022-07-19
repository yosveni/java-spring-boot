package com.linkapital.core.services.commission.datasource.domain;

import com.linkapital.core.services.security.datasource.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity(name = "TAB_COMMISSION_CAMPAIGN_USER")
@SequenceGenerator(name = "gen_commission_campaign_user", sequenceName = "seq_commission_campaign_user", allocationSize = 1)
public class CommissionCampaignUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_campaign_user")
    private Long id;

    private int count;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "campaign_id", nullable = false)
    private CommissionCampaign campaign;

    public CommissionCampaignUser withCount(int count) {
        setCount(count);
        return this;
    }

    public CommissionCampaignUser withUser(User user) {
        setUser(user);
        return this;
    }

    public CommissionCampaignUser withCampaign(CommissionCampaign campaign) {
        setCampaign(campaign);
        return this;
    }

}
