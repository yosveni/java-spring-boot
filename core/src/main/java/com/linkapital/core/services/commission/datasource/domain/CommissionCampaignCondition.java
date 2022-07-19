package com.linkapital.core.services.commission.datasource.domain;

import com.linkapital.core.services.commission.contract.enums.CampaignConnector;
import com.linkapital.core.services.commission.contract.enums.CampaignOperator;
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

import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMMISSION_CAMPAIGN_CONDITION")
@SequenceGenerator(name = "gen_commission_campaign_condition", sequenceName = "seq_commission_campaign_condition", allocationSize = 1)
public class CommissionCampaignCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_campaign_condition")
    private Long id;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private CampaignOperator operator;

    private CampaignConnector connector;

    @OneToOne(orphanRemoval = true, cascade = MERGE)
    @JoinColumn(name = "campaign_attribute_id", nullable = false)
    private CommissionCampaignAttribute campaignAttribute;

}
