package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.services.shared.datasource.domain.Cafir;
import com.linkapital.core.services.shared.datasource.domain.Property;
import com.linkapital.core.services.shared.datasource.domain.PropertyRural;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Boolean.TRUE;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Mapper
public interface PropertiesBinder {

    //Level 1
    String properties = "imoveis";
    String cafir = "cafir";
    //Level 2
    String buildingData = "anoConstrucao";
    String builtArea = "areaConstruida";
    String groundArea = "areaTerreno";
    String evaluationValue = "valorAvaliacao";
    String registryNumber = "idReferencia";
    String addressPerson = "endereco";
    String totalArea = "areaTotal";
    String quantityCondominiums = "numeroImoveisCondomino";
    String quantityHolder = "numeroImoveisTitular";
    //Level 3
    String area = "area";
    String condominium = "condominioInfo";
    String nirf = "nirf";
    String name = "nome";
    String type = "tipo";
    String uf = "uf";
    String registryUf = "ufRegistro";
    String neighborhood = "bairro";
    String cep = "cep";
    String address2 = "complemento";
    String address1 = "logradouro";
    String municipality = "municipio";
    String number = "numero";
    String numberAddressCompany = "numeroLogradouro";

    BiFunction<Map, Boolean, Address> buildAddress = (source, isPerson) -> {
        var address = new Address();
        address.setAddress1(nonNull(source.get(address1))
                ? source.get(address1).toString()
                : null);
        address.setAddress2(nonNull(source.get(address2))
                ? source.get(address2).toString()
                : null);
        address.setNeighborhood(nonNull(source.get(neighborhood))
                ? source.get(neighborhood).toString()
                : null);
        address.setOriginalNeighborhood(address.getNeighborhood());
        address.setZip(nonNull(source.get(cep))
                ? source.get(cep).toString()
                : null);
        address.setMunicipality(nonNull(source.get(municipality))
                ? source.get(municipality).toString()
                : null);
        address.setUf(nonNull(source.get(uf))
                ? source.get(uf).toString()
                : null);

        if (TRUE.equals(isPerson)) {
            address.setNumber(nonNull(source.get(number))
                    ? source.get(number).toString()
                    : null);
        } else {
            address.setNumber(nonNull(source.get(numberAddressCompany))
                    ? source.get(numberAddressCompany).toString()
                    : null);
            address.setRegistryUf(nonNull(source.get(registryUf))
                    ? source.get(registryUf).toString()
                    : null);
        }

        return address;
    };

    Function<Map, Property> buildProperty = source -> {
        var property = new Property();
        property.setEvaluationValue(nonNull(source.get(evaluationValue))
                ? parseDouble(source.get(evaluationValue).toString())
                : 0);
        property.setBuildingData(nonNull(source.get(buildingData))
                ? source.get(buildingData).toString()
                : null);
        property.setBuiltArea(nonNull(source.get(builtArea))
                ? parseDouble(source.get(builtArea).toString())
                : 0);
        property.setGroundArea(nonNull(source.get(groundArea))
                ? parseDouble(source.get(groundArea).toString())
                : 0);
        property.setRegistryNumber(nonNull(source.get(registryNumber))
                ? source.get(registryNumber).toString()
                : null);
        property.setEvaluationValue(nonNull(source.get(evaluationValue))
                ? parseDouble(source.get(evaluationValue).toString())
                : 0);
        property.setAddress(buildAddress.apply(source, false));

        return property;
    };

    Function<List<Map>, Set<Property>> bindCompanyProperty = source -> source
            .stream()
            .map(buildProperty)
            .collect(toSet());

    Function<List<Map>, List<Property>> bindPersonProperty = source -> source
            .stream()
            .map(buildProperty)
            .toList();

    Function<PropertyRural, AddressTO> buildDefaultAddress = source -> {
        var to = new AddressTO();
        to.setMunicipality(source.getMunicipality());
        to.setUf(source.getUf());

        return to;
    };

    Function<Map, PropertyRural> buildPropertyRural = source -> {
        var propertyRural = new PropertyRural();
        propertyRural.setArea(nonNull(source.get(area))
                ? parseDouble(source.get(area).toString())
                : 0);
        propertyRural.setCondominium(nonNull(source.get(condominium))
                ? source.get(condominium).toString()
                : null);
        propertyRural.setMunicipality(nonNull(source.get(municipality))
                ? source.get(municipality).toString()
                : null);
        propertyRural.setNirf(nonNull(source.get(nirf))
                ? source.get(nirf).toString()
                : null);
        propertyRural.setName(nonNull(source.get(name))
                ? source.get(name).toString()
                : null);
        propertyRural.setType(nonNull(source.get(type))
                ? source.get(type).toString()
                : null);
        propertyRural.setUf(nonNull(source.get(uf))
                ? source.get(uf).toString()
                : null);

        return propertyRural;
    };

    Function<List<Map>, List<PropertyRural>> bindPropertyRural = source -> source
            .stream()
            .map(buildPropertyRural)
            .toList();

    Function<Map, Cafir> bindCafir = source -> {
        var target = new Cafir();
        target.setQuantityCondominiums(nonNull(source.get(quantityCondominiums))
                ? parseInt(source.get(quantityCondominiums).toString())
                : 0);
        target.setQuantityHolder(nonNull(source.get(quantityHolder))
                ? parseInt(source.get(quantityHolder).toString())
                : 0);
        target.setTotalArea(nonNull(source.get(totalArea))
                ? parseDouble(source.get(totalArea).toString())
                : 0);

        var list = source.get(properties) == null
                ? null
                : (List<Map>) source.get(properties);
        if (list != null)
            target.getPropertiesRural().addAll(bindPropertyRural.apply(list));

        return target;
    };

}
