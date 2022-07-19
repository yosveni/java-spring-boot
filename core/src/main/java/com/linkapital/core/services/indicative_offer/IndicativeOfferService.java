package com.linkapital.core.services.indicative_offer;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;

/**
 * Default interface for {@link IndicativeOfferService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link IndicativeOffer}
 *
 * @since 0.0.1
 */
public interface IndicativeOfferService {

    /**
     * Save indicative offer
     *
     * @param indicativeOffer {@link IndicativeOffer} the indicative offer to be saved
     * @return {@link IndicativeOffer}
     */
    IndicativeOffer save(IndicativeOffer indicativeOffer);

    /**
     * Get indicative offer by id
     *
     * @param id {@link Long} the indicative offer id
     * @return {@link CompanyUser} a company to add the given learning offer
     * @throws UnprocessableEntityException if the indicative offer is not found
     */
    IndicativeOffer getById(long id) throws UnprocessableEntityException;

    /**
     * Get indicative offer for the company by the given learning number
     *
     * @param number      {@link Integer} the indicative offer number of the company user relation
     * @param companyUser {@link CompanyUser} the company user relation
     * @return {@link CompanyUser}
     */
    CompanyUser getIndicativeOffer(int number, CompanyUser companyUser);

    /**
     * Update the indicative offer
     *
     * @param to {@link UpdateIndicativeOfferTO} the updated offer
     * @return {@link IndicativeOfferTO}
     * @throws UnprocessableEntityException if don't exists a company by the given cnpj
     */
    IndicativeOfferTO updateIndicativeOffer(UpdateIndicativeOfferTO to) throws UnprocessableEntityException;

}
