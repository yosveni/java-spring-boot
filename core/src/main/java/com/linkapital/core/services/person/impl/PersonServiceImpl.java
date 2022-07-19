package com.linkapital.core.services.person.impl;

import com.linkapital.core.exceptions.CpfNotFoundException;
import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.integrations.NeoWaySearchService;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.contract.to.CreatePartnerSpouseTO;
import com.linkapital.core.services.person.contract.to.IrpfDocumentsTO;
import com.linkapital.core.services.person.datasource.PersonRepository;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.directory.contract.enums.Type.IRPF;
import static com.linkapital.core.services.directory.contract.enums.Type.IRPF_RECEIPT;
import static com.linkapital.core.services.person.contract.PersonBinder.mergeDocumentList;
import static com.linkapital.core.services.person.contract.PersonBinder.validateCpfLists;
import static com.linkapital.core.services.person.validator.PersonValidator.validateIrpfFiles;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

/**
 * Has the responsibility to make operations over person.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<PersonConverter> personConverters;
    private final List<PersonConverter> personConvertersDependents;
    private final PersonRepository personRepository;
    private final NeoWaySearchService neoWaySearchService;
    private final DirectoryService directoryService;

    public PersonServiceImpl(PersonRepository personRepository, NeoWaySearchService neoWaySearchService,
                             DirectoryService directoryService) {
        this.personRepository = personRepository;
        this.neoWaySearchService = neoWaySearchService;
        this.directoryService = directoryService;
        this.personConverters = new ArrayList<>();
        this.personConvertersDependents = new ArrayList<>();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public IrpfDocumentsTO uploadFile(String cpf,
                                      MultipartFile[] fileIrpf,
                                      MultipartFile[] fileIrpfReceipt,
                                      boolean partner) throws ResourceNotFoundException, UnprocessableEntityException {
        var person = getByCpf(cpf);

        validateIrpfFiles(fileIrpf, fileIrpfReceipt);
        if (partner)
            return mergeDocumentList.apply(uploadFileAux(person, fileIrpf, fileIrpfReceipt));
        else if (person.getSpouse() == null)
            throw new UnprocessableEntityException(msg("person.has.no.spouse"));

        return mergeDocumentList.apply(uploadFileAux(person.getSpouse(), fileIrpf, fileIrpfReceipt));
    }

    @Override
    public Person getByCpfAux(String cpf) {
        return personRepository.findByCpf(cpf).orElse(null);
    }

    @Override
    public List<Person> getPersonPartnerData(List<Person> partners, List<CreatePartnerSpouseTO> partnersSpouse)
            throws UnprocessableEntityException {
        var cpfPersonsPartners = new ArrayList<String>();
        var cpfPersonSpouse = new ArrayList<String>();

        partnersSpouse.forEach(to -> {
            cpfPersonsPartners.add(to.getCpfPartner());

            if (hasText(to.getCpfSpouse()))
                cpfPersonSpouse.add(to.getCpfSpouse());
        });

        validateCpfLists(cpfPersonsPartners, cpfPersonSpouse, partners);

        var personPartners = getPersonPartnerPopulated(partners, cpfPersonsPartners);
        var personSpouse = getPersonSpousePopulated(cpfPersonSpouse);

        return personSpouse.isEmpty()
                ? personPartners
                : relatedPartnersSpouses(partnersSpouse, personPartners, personSpouse);
    }

    @Override
    public List<Person> getAllByCpfIn(List<String> cpfList) {
        return personRepository.findAllByCpfIn(cpfList);
    }

    @Override
    public void addConverter(PersonConverter personConverter) {
        this.personConverters.add(personConverter);
    }

    @Override
    public void addConverterDependent(PersonConverter personConverter) {
        this.personConvertersDependents.add(personConverter);
    }

    @Override
    public List<Person> getPersonPartners(Company company) {
        return company.getPartners()
                .stream()
                .map(CompanyPartners::getPerson)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Person> getPersonPartnerFromApi(List<Person> personPartners) {
        return personPartners
                .stream()
                .map(person -> {
                    if (person.isDataNeoWay())
                        return person;

                    try {
                        return getByCpfInNeoWay(person);
                    } catch (UnprocessableEntityException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    //region Gets a person by CPF code given.
    private Person getByCpf(String cpf) throws ResourceNotFoundException {
        return personRepository
                .findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException(msg("person.cpf.not.found", cpf)));
    }
    //endregion

    //region Retrieves a person from NeoWay API by CPF code given.
    private Person getByCpfInNeoWay(Person person) throws UnprocessableEntityException {
        try {
            return getFromNeoWay(person.getCpf(), person);
        } catch (CpfNotFoundException | ParseException e) {
            throw new UnprocessableEntityException(msg("person.not.found.by.ia"));
        }
    }
    //endregion

    //region Retrieves a person from NeoWay API.
    private Person getFromNeoWay(String cpf, Person person) throws CpfNotFoundException, ParseException {
        var data = neoWaySearchService.getCpfData(cpf);
        if (data == null)
            throw new CpfNotFoundException(msg("person.cpf.not.found", cpf));

        person.setDataNeoWay(true);
        addConverters(data, person, personConverters);
        person = personRepository.save(person);
        addConverters(data, person, personConvertersDependents);

        return person;
    }
    //endregion

    //region Populates person's data by person and data given.
    private void addConverters(Map data, Person person, List<PersonConverter> personConverters) throws ParseException {
        if (!personConverters.isEmpty())
            for (PersonConverter personConverter : personConverters)
                person = personConverter.convert(person, data);
    }
    //endregion

    //region Relates each partner and spouse and saves it in data base.
    private List<Person> relatedPartnersSpouses(List<CreatePartnerSpouseTO> partnersSpouse, List<Person> persons,
                                                List<Person> personSpouse) {
        partnersSpouse
                .forEach(partnerSpouse -> persons
                        .stream()
                        .filter(person -> person.getCpf().equals(partnerSpouse.getCpfPartner()))
                        .findFirst()
                        .ifPresent(person -> personSpouse
                                .stream()
                                .filter(spouse -> spouse.getCpf().equals(partnerSpouse.getCpfSpouse()))
                                .findFirst()
                                .ifPresent(spouse -> {
                                    if (hasText(partnerSpouse.getMarriageRegime())) {
                                        person.setMarriageRegime(partnerSpouse.getMarriageRegime());
                                        spouse.setMarriageRegime(partnerSpouse.getMarriageRegime());
                                    }
                                    person.setSpouse(spouse);
                                    spouse.setSpouse(person);
                                })));

        return personRepository.saveAll(persons);
    }
    //endregion

    //region Gets a partners list with their data populated by CPF given.
    private List<Person> getPersonPartnerPopulated(List<Person> persons,
                                                   List<String> cpfPersonsPartners) throws UnprocessableEntityException {
        var personPartners = persons
                .stream()
                .filter(person -> cpfPersonsPartners.contains(person.getCpf()))
                .collect(toList());

        if (personPartners.size() != cpfPersonsPartners.size())
            throw new UnprocessableEntityException(msg("person.partner.not.found"));

        return getPersonPartnerFromApi(personPartners);
    }
    //endregion

    //region Gets a partners's spouses list with their data populated by CPF given.
    private List<Person> getPersonSpousePopulated(List<String> cpfPersonSpouse) {
        var personSpouse = personRepository.findAllByCpfIn(cpfPersonSpouse);

        if (personSpouse.size() < cpfPersonSpouse.size())
            cpfPersonSpouse.forEach(s -> personSpouse
                    .stream()
                    .filter(person -> person.getCpf().equals(s))
                    .findFirst()
                    .ifPresentOrElse(person -> {
                    }, () -> personSpouse.add(new Person()
                            .withCpf(s))));

        return getPersonPartnerFromApi(personSpouse);
    }
    //endregion

    //region Upload of files by type and person given.
    private void uploadFiles(MultipartFile[] files, Type type, Person person) {
        if (files.length == 0) return;

        var directories = directoryService.uploadIrpfDocuments(person, files, type);
        switch (type) {
            case IRPF -> setDocuments(person.getIrpfDocuments(), directories);
            case IRPF_RECEIPT -> setDocuments(person.getIrpfReceiptDocuments(), directories);
            default -> {
            }
        }
    }
    //endregion

    //region Clears a documents list and fills it with new information given.
    private void setDocuments(List<Directory> documents, List<Directory> directories) {
        directoryService.deleteFiles(documents);
        documents.clear();
        documents.addAll(directories);
    }
    //endregion

    private Person uploadFileAux(Person person, MultipartFile[] fileIrpf, MultipartFile[] fileIrpfReceipt) {
        uploadFiles(fileIrpf, IRPF, person);
        uploadFiles(fileIrpfReceipt, IRPF_RECEIPT, person);

        return save(person);
    }

}
