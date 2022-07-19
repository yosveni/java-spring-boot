package com.linkapital.core.services.industrial_production_index;

import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;

import java.util.List;

/**
 * Default interface for {@link PhysicalProductionService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link PhysicalProduction}
 *
 * @since 0.0.1
 */
public interface PhysicalProductionService {

    /**
     * Given the company's cnpj, it performs searches in the Sidra API to obtain the data referring to the company's
     * bank disbursement.
     *
     * @param cnae {@link String} the company main cnae
     * @return {@link List}<{@link PhysicalProduction}> the company's bank operations list
     */
    List<PhysicalProduction> getPhysicalProduction(String cnae);

}
