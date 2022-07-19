package com.linkapital.core.services.cri_cra_debenture;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CreateCriCraDebentureListTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;

import java.util.List;

/**
 * Default interface for {@link CriCraDebentureService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link CriCraDebenture}
 *
 * @since 0.0.1
 */
public interface CriCraDebentureService {

    /**
     * Save the given company in the datasource
     *
     * @param criCraDebentureTOList {@link CreateCriCraDebentureListTO} the elements to save
     * @return {@link List}<{@link CriCraDebentureTO}>
     */
    List<CriCraDebentureTO> createCriCraDebentures(CreateCriCraDebentureListTO criCraDebentureTOList) throws UnprocessableEntityException;

}
