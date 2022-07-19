package com.linkapital.core.services.person;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.person.contract.to.CreatePartnerSpouseTO;
import com.linkapital.core.services.person.contract.to.IrpfDocumentsTO;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link PersonService}
 * Has the responsibility to make operations over the {@link Person} entity
 *
 * @since 0.0.1
 */
public interface PersonService {

    /**
     * Saves in the data base the given person entity
     *
     * @param person {@link Person} the entity to be saved
     * @return {@link Person} the entity saved
     */
    Person save(Person person);

    /**
     * Gets a person by the given CPF code
     *
     * @param cpf {@link String} the person CPF code
     * @return {@link Person} a person entity, if the person does not exist it returns null
     */
    Person getByCpfAux(String cpf);

    /**
     * Upload of IRPF files and IRPF Receipt files for the partner and his spouse
     *
     * @param cpf             {@link String} the CPF of the person partner
     * @param fileIrpf        an array of {@link MultipartFile}[] the files IRPF of the person
     * @param fileIrpfReceipt an array of {@link MultipartFile}[] the files IRPF Receipt of the person
     * @param partner         {@link boolean} indicates if the person is partner
     * @return {@link IrpfDocumentsTO} a container of IRPF documents and IRPF Receipt documents
     * @throws ResourceNotFoundException    if the person is not found
     * @throws UnprocessableEntityException if any error occur retrieving the person data
     */
    IrpfDocumentsTO uploadFile(String cpf, MultipartFile[] fileIrpf, MultipartFile[] fileIrpfReceipt, boolean partner) throws ResourceNotFoundException, UnprocessableEntityException;

    /**
     * Gets the list of members given a company
     *
     * @param company {@link Company} the company
     * @return {@link List}<{@link Person}> the persons partners list
     */
    List<Person> getPersonPartners(Company company);

    /**
     * Looks for the data of each person in the API to consume
     *
     * @param partners       {@link List}<{@link Person}> the list of person partners
     * @param partnersSpouse {@link List}<{@link CreatePartnerSpouseTO}> the cpf list of partners of the company and
     *                       their spouses
     * @return {@link List}<{@link Person}> the list of person partners of the company with their updated data
     * @throws UnprocessableEntityException if any error occur retrieving the person data
     */
    List<Person> getPersonPartnerData(List<Person> partners, List<CreatePartnerSpouseTO> partnersSpouse) throws UnprocessableEntityException;

    /**
     * Finds all the persons that belongs to the given CPF list
     *
     * @param cpfList {@link List}<{@link String}> a list of CPF codes
     * @return {@link List}<{@link Person}> a list of persons that belongs to the given CPF list
     */
    List<Person> getAllByCpfIn(List<String> cpfList);

    /**
     * Looks for the data of each person in the API to consume.
     *
     * @param personPartners the persons partner list
     * @return {@link List}<{@link Person}> the person list
     */
    List<Person> getPersonPartnerFromApi(List<Person> personPartners);

    /**
     * Adds a converter to the service person converter list
     *
     * @param personConverter {@link PersonConverter} a converted interface implementation
     */
    void addConverter(PersonConverter personConverter);

    /**
     * Adds a converter to the service person converter depends list
     *
     * @param personConverter {@link PersonConverter} a converted interface implementation
     */
    void addConverterDependent(PersonConverter personConverter);

}
