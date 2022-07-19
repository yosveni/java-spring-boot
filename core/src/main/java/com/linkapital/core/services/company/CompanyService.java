package com.linkapital.core.services.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.contract.to.CompanyLocationTO;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.contract.to.InitLearningOneTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.offer.contract.enums.FilterOfferType;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.generic.GenericFilterTO;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Default interface for {@link CompanyService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link Company}
 *
 * @since 0.0.1
 */
public interface CompanyService {

    /**
     * Save the given company in the datasource
     *
     * @param company {@link Company} the company to be register in datasource
     * @return {@link Company} the company entity saved in datasource
     */
    Company save(Company company);

    /**
     * Process Init Learning One, throw error if the user has init learning one for a cnpj
     *
     * @param user {@link User}
     * @param to   {@link InitLearningOneTO}
     * @return {@link CompanyUser} the CompanyClientTO with the latest registration form field updated
     * @throws UnprocessableEntityException if error occurs
     */
    CompanyUser processInitLearningOne(User user, InitLearningOneTO to) throws UnprocessableEntityException;

    /**
     * Retrieve a company from the datasource by cnpj code
     *
     * @param cnpj {@link String} the cnpj of the company
     * @return {@link Company} a company found in datasource
     * @throws ResourceNotFoundException if not found a {@link Company} entity in datasource by the given cnpj code
     */
    Company getByCnpj(String cnpj) throws ResourceNotFoundException;

    /**
     * Retrieve a company from the datasource by cnpj code
     *
     * @param cnpj {@link String} the cnpj of the company
     * @return {@link Company} a company found in datasource
     */
    Company getByCnpjAux(String cnpj);

    /**
     * Return all companies
     *
     * @return {@link List}<{@link Company}> the companies list
     */
    List<Company> getAll();

    /**
     * Given a list of cnpj returns the companies referents
     *
     * @param cnpjList {@link List}<{@link String}> the list of cnpj
     * @return list of {@link Company} the companies of each cnpj in the list
     */
    List<Company> getByCnpjIn(List<String> cnpjList);

    /**
     * Get all the persons partners of the company
     *
     * @param cnpj {@link String} the cnpj of the company
     * @return {@link List}<{@link Person}> the list of persons partners
     * @throws ResourceNotFoundException if not found a {@link Company} with the given cnpj code
     */
    List<Person> getAllPartners(String cnpj) throws ResourceNotFoundException;

    /**
     * Gets filtered and sorts companies.
     *
     * @param filter {@link String} the filter
     * @param page   {@link Integer} the page number
     * @param size   {@link Integer} the page size
     * @param sort   {@link String} the sort
     * @param order  {@link String} the order
     * @return {@link GenericFilterTO}<{@link LightBackOfficeTO}>
     * @throws UnprocessableEntityException if there is any error in the filters
     */
    Page<CompanyUser> getByFilterGlobal(FilterOfferType type, String filter, Integer page, Integer size, String sort, String order) throws UnprocessableEntityException;

    GenericFilterTO<LightBackOfficeTO> getListLightBackOfficeTO(FilterOfferType type, String filter, Integer page, Integer size, String sort, String order) throws UnprocessableEntityException;

    /**
     * Get all duplicate CNPJs
     *
     * @return {@link List}<{@link String}> the list of cnpj
     */
    List<String> getAllCnpjRepeat();

    /**
     * Get all the companies within a radius of location
     *
     * @param latitude  {@link Double} the latitude
     * @param longitude {@link Double} the longitude
     * @param radius    {@link Double} the radio (default 1000M)
     * @return {@link List}<{@link CompanyLocationTO}>
     */
    List<CompanyLocationTO> getAllCompaniesInRadioLocation(double latitude, double longitude, double radius);

    /**
     * Update the credit information of a company given its cnpj.
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link List}<{@link CreditInformationTO}> the credits information list
     * @throws UnprocessableEntityException if error occurs
     */
    List<CreditInformationTO> updateCreditsInformationByCnpj(String cnpj) throws UnprocessableEntityException;

    /**
     * Build the company list from the given company list retrieved from {@see saveBackOffice} method
     * Also populate the cnpjErrorsNotFound list with all error getting in the building company list process
     *
     * @param user                {@link User}
     * @param cnpjList            {@link List}<{@link String}> the list of cnpj
     * @param companyDatabaseList {@link List}<{@link Company}>
     * @return {@link List}<{@link Company}>
     */
    List<Company> buildRelations(User user, List<String> cnpjList, List<Company> companyDatabaseList) throws UnprocessableEntityException;

    /**
     * Add a converter to list of converters
     *
     * @param companyConverter {@link CompanyConverter} an implementation of CompanyConverter interface
     */
    void addConverter(CompanyConverter companyConverter);

    /**
     * Add a converter to the dependent list of converters
     *
     * @param companyConverter {@link CompanyConverter} an implementation of CompanyConverter interface
     */
    void addConverterDependent(CompanyConverter companyConverter);

    /**
     * Save all companies of the list
     *
     * @param companies {@link List}<{@link Company}> a list of the companies to be register
     */
    void saveAll(@NotNull List<Company> companies);

    /**
     * Reset company owner.
     *
     * @param cnpj {@link String} the cnpj
     * @throws ResourceNotFoundException if not found a {@link Company} entity for the given cnpj
     */
    void resetOwner(String cnpj) throws ResourceNotFoundException;

    /**
     * Delete a company by cnpj
     *
     * @param cnpj {@link String} the company cnpj
     * @throws ResourceNotFoundException if not found a {@link Company} with the given cnpj code
     */
    void delete(String cnpj) throws ResourceNotFoundException;

    /**
     * Delete a company by id
     *
     * @param id {@link Long} the company id
     * @throws ResourceNotFoundException if not found a {@link Company} with the given cnpj code
     */
    void delete(Long id) throws ResourceNotFoundException;

    /**
     * Get a company from Neo Way api, and apply the configured converters for the company
     *
     * @param cnpj    {@link Long} the company cnpj
     * @param company {@link Company} the company
     * @throws UnprocessableEntityException if error occurs
     */
    Company getFromExternalApi(String cnpj, @NotNull Company company) throws UnprocessableEntityException;

}
