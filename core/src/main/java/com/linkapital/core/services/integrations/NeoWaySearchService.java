package com.linkapital.core.services.integrations;

import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.CpfNotFoundException;

import java.util.Map;

/**
 * Default interface for {@link NeoWaySearchService}\
 * <p>
 * Service with the responsibility of performing search operations on the NeoWay service API
 *
 * @since 0.0.1
 */
public interface NeoWaySearchService {

    /**
     * Retrieve data from NeoWay service by the given cnpj code
     *
     * @param cnpj {@link String} the given cnpj code
     * @return {@link Map} the data retrieved form de NeoWay API
     * @throws CnpjNotFoundException if the cnpj is not found
     */
    Map getCnpjData(String cnpj) throws CnpjNotFoundException;

    /**
     * Retrieve data from NeoWay service by the given cpf code
     *
     * @param cpf {@link String} the given cpf code
     * @return {@link Map} the data retrieved form de NeoWay API
     * @throws CpfNotFoundException if the cpf is not found
     */
    Map getCpfData(String cpf) throws CpfNotFoundException;

}
