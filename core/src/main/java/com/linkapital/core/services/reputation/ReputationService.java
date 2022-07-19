package com.linkapital.core.services.reputation;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.reputation.contract.to.CreateRatingTO;
import com.linkapital.core.services.reputation.contract.to.ReputationTO;

/**
 * Default interface for {@link ReputationService}
 * Has the responsibility of executing the operations related to the ratings of the system
 *
 * @since 0.0.1
 */
public interface ReputationService {

    /**
     * Create the rating to the system
     *
     * @param to {@link CreateRatingTO} the data to create the rating
     * @return {@link ReputationTO} the data of the reputation
     * @throws UnprocessableEntityException if the user in question has already made the rating
     */
    ReputationTO createRate(CreateRatingTO to) throws UnprocessableEntityException;

    /**
     * Returns the reputation of the system
     *
     * @return {@link ReputationTO} the data of the reputation
     */
    ReputationTO get();

}
