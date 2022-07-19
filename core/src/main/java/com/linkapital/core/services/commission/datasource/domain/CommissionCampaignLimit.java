package com.linkapital.core.services.commission.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMMISSION_CAMPAIGN_LIMIT")
@SequenceGenerator(name = "gen_commission_campaign_limit", sequenceName = "seq_commission_campaign_limit", allocationSize = 1)
public class CommissionCampaignLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_campaign_limit")
    private Long id;

    @Column(name = "limit_count")
    private int limitCount;

    @Column(name = "campaign_limit")
    private int campaignLimit;

    @Column(name = "user_limit")
    private int userLimit;

}
