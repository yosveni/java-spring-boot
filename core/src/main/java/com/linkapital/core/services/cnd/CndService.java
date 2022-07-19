package com.linkapital.core.services.cnd;

/**
 * Default interface for {@link CndService}
 * Has the responsibility of search for documents related to the CND by scraping
 *
 * @since 0.0.1
 */
public interface CndService {

    /**
     * Scraping the site
     * <a href="https://contas.tcu.gov.br/ords/f?p=1660:3:::NO:3,4,6::&cs=3FNPnH1AEv6jNHC_fkP2nNzX_okw">Site</a>
     * and the negative process is returned an {@link byte[]} array of by.
     *
     * @param cnpj {@link String} the cnpj
     * @return {@link byte[]} array of by
     */
    byte[] searchTcu(String cnpj);

    byte[] searchTst(String cnpj);

}
