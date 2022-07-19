package com.linkapital.core.services.protest;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.protest.datasource.domain.ProtestInformation;

/**
 * Default interface for {@link ProtestService}
 * Service with the responsibility of looking for protests given a document (cpf or cnpj)
 *
 * @since 0.0.1
 */
public interface ProtestService {

    /**
     * Looking for protests given a document (cpf or cnpj)
     *
     * @param document {@link String} the cnpj or cpf code
     * @return {@link ProtestInformation}
     */
    ProtestInformation getProtestInformation(String document) throws UnprocessableEntityException;

}
