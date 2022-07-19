package com.linkapital.core.services.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.security.datasource.domain.User;

public interface CompanyMaintenanceService {

    void updateForMaintenance(String cnpj) throws ResourceNotFoundException, UnprocessableEntityException;

    void updateAllMaintenance() throws UnprocessableEntityException;

    void deleteCompanyById(long id);

    void deleteCompanyUserId(long id);

    void deleteAllNotClient();

    /**
     * Remove relationships from {@link CompanyUser} given a user id
     *
     * @param id {@link long} the user id
     * @throws UnprocessableEntityException if not exists a {@link User} with the given id
     */
    void deleteCompanyUser(long id) throws UnprocessableEntityException;

    /**
     * Deleting all companies and their relationships with the company_user
     */
    void deleteAllCompany();

}
