package com.linkapital.core.services.property_guarantee;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.property_guarantee.contract.to.CreatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.UpdatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import org.springframework.web.multipart.MultipartFile;

/**
 * Default interface for {@link PropertyGuaranteeService}
 * Service with the responsibility of performing operations on the {@link PropertyGuarantee} entity
 *
 * @since 0.0.1
 */
public interface PropertyGuaranteeService {

    /**
     * Create property guarantee.
     *
     * @param to {@link CreatePropertyGuaranteeTO} the property guarantee data
     * @return {@link PropertyGuaranteeTO}
     * @throws UnprocessableEntityException if error occurs
     */
    PropertyGuaranteeTO create(CreatePropertyGuaranteeTO to) throws UnprocessableEntityException;

    /**
     * Update property guarantee.
     *
     * @param to {@link UpdatePropertyGuaranteeTO} the property guarantee data
     * @return {@link PropertyGuaranteeTO}
     * @throws UnprocessableEntityException if error occurs
     */
    PropertyGuaranteeTO update(UpdatePropertyGuaranteeTO to) throws UnprocessableEntityException;

    /**
     * Upload document of property guarantee.
     *
     * @param id   {@link Long} the property guarantee id
     * @param file {@link MultipartFile} the file
     * @return {@link PropertyGuaranteeTO}
     * @throws UnprocessableEntityException if error occurs
     */
    PropertyGuaranteeTO uploadDocument(long id, MultipartFile file) throws UnprocessableEntityException;

    /**
     * Delete property guarantee.
     *
     * @param id {@link Long} the property guarantee id
     * @throws UnprocessableEntityException if error occurs
     */
    void delete(long id) throws UnprocessableEntityException;

    /**
     * Delete property guarantee document.
     *
     * @param id {@link Long} the property guarantee id
     * @throws UnprocessableEntityException if error occurs
     */
    void deleteDocument(long id) throws UnprocessableEntityException;

}
