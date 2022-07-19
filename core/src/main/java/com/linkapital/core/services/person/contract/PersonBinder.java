package com.linkapital.core.services.person.contract;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.person.contract.to.IrpfDocumentsTO;
import com.linkapital.core.services.person.contract.to.LightPersonSpouseTO;
import com.linkapital.core.services.person.contract.to.PersonPartnerTO;
import com.linkapital.core.services.person.datasource.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.property_guarantee.contract.PropertyGuaranteeBinder.PROPERTY_GUARANTEE_BINDER;
import static com.linkapital.core.services.property_guarantee.contract.PropertyGuaranteeBinder.bindCafirToListGuaranteeTO;
import static com.linkapital.core.services.property_guarantee.contract.enums.OwnerType.PARTNER;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@Mapper
public interface PersonBinder {

    PersonBinder PERSON_BINDER = Mappers.getMapper(PersonBinder.class);

    Function<Person, IrpfDocumentsTO> mergeDocumentList = source -> new IrpfDocumentsTO()
            .withIrpfDocuments(DIRECTORY_BINDER.bind(source.getIrpfDocuments()))
            .withIrpfReceiptDocuments(DIRECTORY_BINDER.bind(source.getIrpfReceiptDocuments()));

    //region Validates there are no spouses in the list of partners.
    //     In addition to that the list of CPF of selected partners coincides with the partners of the company.
    static void validateCpfLists(List<String> cpfPersonsPartners, List<String> cpfPersonSpouse,
                                 List<Person> personPartners) throws UnprocessableEntityException {
        validateRepeat(cpfPersonsPartners, cpfPersonSpouse);

        var cpfCompanyPartners = personPartners
                .stream()
                .map(Person::getCpf)
                .toList();

        for (String cpf : cpfPersonsPartners)
            if (!cpfCompanyPartners.contains(cpf))
                throw new UnprocessableEntityException(msg("person.company.partner.cpf.not.found", cpf));

        for (String cpf : cpfPersonSpouse)
            if (cpfCompanyPartners.contains(cpf))
                throw new UnprocessableEntityException(msg("person.spouse.cpf.not.found", cpf));
    }

    static void validateRepeat(List<String> cpfPersonsPartners,
                               List<String> cpfPersonSpouse) throws UnprocessableEntityException {
        var repeatPersonsPartners = new AtomicBoolean(false);
        var repeatPersonsSpouse = new AtomicBoolean(false);

        cpfPersonsPartners
                .stream()
                .collect(groupingBy(s -> s))
                .forEach((s, strings) -> {
                    if (strings.size() > 1)
                        repeatPersonsPartners.set(true);
                });

        if (repeatPersonsPartners.get())
            throw new UnprocessableEntityException(msg("person.partner.repeat"));

        cpfPersonSpouse
                .stream()
                .collect(groupingBy(s -> s))
                .forEach((s, strings) -> {
                    if (strings.size() > 1)
                        repeatPersonsSpouse.set(true);
                });

        if (repeatPersonsSpouse.get())
            throw new UnprocessableEntityException(msg("person.spouse.repeat"));
    }

    List<PersonPartnerTO> bind(List<Person> source);

    @Mapping(target = "spouse", expression = "java(source.getSpouse() != null ? source.getSpouse().getName() : null)")
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "propertiesRural", ignore = true)
    LightPersonSpouseTO bindToPersonSpouseTO(Person source);

    default List<LightPersonSpouseTO> bindToPersonSpouseListTO(List<Person> source) {
        return source
                .stream()
                .map(this::buildLightPersonSpouseTO)
                .toList();
    }

    default List<LightPersonSpouseTO> buildSpouses(List<Person> source) {
        return bindToPersonSpouseListTO(source
                .stream()
                .map(Person::getSpouse)
                .filter(Objects::nonNull)
                .toList());
    }

    default LightPersonSpouseTO buildLightPersonSpouseTO(Person source) {
        return Optional
                .ofNullable(bindToPersonSpouseTO(source))
                .map(to -> to
                        .withProperties(PROPERTY_GUARANTEE_BINDER.bind(source.getProperties(), PARTNER))
                        .withPropertiesRural(source.getCafir() == null
                                ? emptyList()
                                : bindCafirToListGuaranteeTO.apply(source.getCafir().getPropertiesRural(), PARTNER)))
                .orElse(null);
    }

}
