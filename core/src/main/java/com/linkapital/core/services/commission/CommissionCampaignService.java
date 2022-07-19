package com.linkapital.core.services.commission;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;

import java.util.List;

/**
 * Default interface for {@link CommissionCampaignService}
 * Service with the responsibility of carrying out operations and business logic in the entities {@link CommissionCampaign}
 *
 * @since 0.0.1
 */
public interface CommissionCampaignService {

    /**
     * Create commission campaign.
     *
     * @param to {@link CreateCommissionCampaignTO} the commission campaign data
     * @return {@link CommissionCampaignTO}
     */
    CommissionCampaignTO create(CreateCommissionCampaignTO to);

    /**
     * Update commission campaign.
     *
     * @param to {@link UpdateCommissionCampaignTO} the commission campaign data
     * @return {@link CommissionCampaignTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    CommissionCampaignTO update(UpdateCommissionCampaignTO to) throws UnprocessableEntityException;

    /**
     * Get commission campaign by id.
     *
     * @param id {@link Long} the commission campaign id
     * @return {@link CommissionCampaign}
     * @throws UnprocessableEntityException if any error occurs
     */
    CommissionCampaign getById(long id) throws UnprocessableEntityException;

    /**
     * Get base commission campaign.
     *
     * @return {@link CommissionCampaign}
     * @throws UnprocessableEntityException if any error occurs
     */
    CommissionCampaign getMatchBase() throws UnprocessableEntityException;

    /**
     * Returns all commission campaigns.
     *
     * @return {@link List}<{@link CommissionCampaignTO}>
     */
    List<CommissionCampaignTO> getAll();

    /**
     * Get commission campaigns that your conditions meet the offer values.
     *
     * @param offerId {@link Long} the offer id
     * @return {@link List}<{@link CommissionCampaignTO}>
     * @throws UnprocessableEntityException if any error occurs
     */
    List<CommissionCampaignTO> getAllMatchByOffer(long offerId) throws UnprocessableEntityException;

    /**
     * Returns all the attributes to use to conform the conditions of the campaign.
     *
     * @return {@link List}<{@link CommissionCampaignAttributeTO}>
     */
    List<CommissionCampaignAttributeTO> getAllAttributes();

    /**
     * Delete commission campaign by id.
     *
     * @param id {@link Long} the commission id
     * @throws UnprocessableEntityException if any error occurs
     */
    void delete(long id) throws UnprocessableEntityException;

}
