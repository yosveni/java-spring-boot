package com.linkapital.core.services.commission.datasource.domain;

import com.linkapital.core.services.commission.contract.enums.CampaignAttributeType;
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
@Entity(name = "TAB_COMMISSION_CAMPAIGN_ATTRIBUTE")
@SequenceGenerator(name = "gen_commission_campaign_attribute", sequenceName = "seq_commission_campaign_attribute", allocationSize = 1)
public class CommissionCampaignAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_commission_campaign_attribute")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "attribute_type", nullable = false)
    private CampaignAttributeType attributeType;

}
