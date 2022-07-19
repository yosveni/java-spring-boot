package com.linkapital.core.services.ibge;

import com.linkapital.core.services.ibge.datasource.domain.Ibge;

/**
 * Default interface for {@link IbgeService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link Ibge}
 *
 * @since 0.0.1
 */
public interface IbgeService {

    /**
     * Given the data of the federal unit and the municipality of the company, perform searches in the IBGE API,
     * to obtain the geographical statistics of the company
     *
     * @param uf           {@link String} the federal unit where the company belongs
     * @param municipality {@link String} the municipality where the company belongs
     * @return {@link Ibge} the geographical statistics of the company
     */
    Ibge getIbge(String uf, String municipality);

}
