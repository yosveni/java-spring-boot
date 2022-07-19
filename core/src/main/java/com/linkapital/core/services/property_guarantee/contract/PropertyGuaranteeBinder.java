package com.linkapital.core.services.property_guarantee.contract;

import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.property_guarantee.contract.enums.OwnerType;
import com.linkapital.core.services.property_guarantee.contract.to.CreatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.UpdatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.shared.datasource.domain.Property;
import com.linkapital.core.services.shared.datasource.domain.PropertyRural;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.linkapital.core.services.property_guarantee.contract.enums.PropertyType.PROPERTY;
import static com.linkapital.core.services.property_guarantee.contract.enums.PropertyType.PROPERTY_RURAL;
import static com.linkapital.core.services.shared.contract.PropertiesBinder.buildDefaultAddress;

@Mapper
public interface PropertyGuaranteeBinder {

    PropertyGuaranteeBinder PROPERTY_GUARANTEE_BINDER = Mappers.getMapper(PropertyGuaranteeBinder.class);

    BiFunction<List<Person>, PropertyGuaranteeTO, String> find = (persons, property) -> persons
            .stream()
            .filter(person -> person.getProperties()
                    .stream()
                    .anyMatch(to -> to.getReferenceProperty() == property.getReferenceProperty())
                    || (person.getCafir() != null && person.getCafir().getPropertiesRural()
                    .stream()
                    .anyMatch(to -> to.getReferenceProperty() == property.getReferenceProperty())))
            .findFirst()
            .flatMap(to -> Optional.of(to.getCpf()))
            .orElse(null);

    BiFunction<PropertyRural, OwnerType, PropertyGuaranteeTO> bindCafirToGuaranteeTO = (source, ownerType) -> {
        var to = new PropertyGuaranteeTO()
                .withType(PROPERTY_RURAL)
                .withOwnerType(ownerType);
        to.setRegistryNumber(source.getNirf());
        to.setBuiltArea(source.getArea());
        to.setAddress(buildDefaultAddress.apply(source));
        to.setId(source.getId());

        return to;
    };

    BiFunction<List<PropertyRural>, OwnerType, List<PropertyGuaranteeTO>> bindCafirToListGuaranteeTO =
            (source, ownerType) -> source
                    .stream()
                    .map(propertyRural -> bindCafirToGuaranteeTO.apply(propertyRural, ownerType))
                    .toList();

    @Mapping(target = "referenceProperty", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "ownerType", ignore = true)
    @Mapping(target = "document", ignore = true)
    PropertyGuaranteeTO bind(Property source);

    PropertyGuaranteeTO bind(PropertyGuarantee source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "companyUser", ignore = true)
    PropertyGuarantee bind(CreatePropertyGuaranteeTO source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "companyUser", ignore = true)
    PropertyGuarantee set(UpdatePropertyGuaranteeTO source, @MappingTarget PropertyGuarantee target);

    List<PropertyGuaranteeTO> bindToPropertyGuaranteeTO(Collection<PropertyGuarantee> source);

    default List<PropertyGuaranteeTO> bind(Collection<Property> source, OwnerType ownerType) {
        return source
                .stream()
                .map(property -> bind(property)
                        .withType(PROPERTY)
                        .withOwnerType(ownerType))
                .toList();
    }

}
