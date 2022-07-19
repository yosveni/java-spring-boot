package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.datasource.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.linkapital.core.util.DateUtil.convert;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface PersonInfoBinder {

    PersonInfoBinder PERSON_INFO_BINDER = Mappers.getMapper(PersonInfoBinder.class);
    Logger log = getLogger(PersonInfoBinder.class);

    //Level 1
    String cpf = "cpf";
    String name = "nome";
    String sex = "sexo";
    String cns = "cns";
    String nis = "nis";
    String email = "email";
    String motherCpf = "cpfMae";
    String motherName = "nomeMae";
    String fatherName = "nomePai";
    String country = "originCountry";
    String birthDate = "dataNascimento";
    String socialInscription = "pis";
    String debitPgfnDau = "faixaDividaPgfnDau";
    String situationCpf = "situacaoCpf";
    String educationLevel = "grauInstrucao";
    String age = "idade";
    String deficiency = "portadorDeficiencia";
    String deficiencyType = "tipoDeficiencia";
    String deadConfirmation = "falecidoConfirmado";
    String deadDate = "anoFalecimento";
    String inscriptionCpfDate = "cpfDataInscricao";
    String phones = "telefones";
    String address = "endereco";
    String addresses = "enderecoOutros";
    String publicAgent = "servidorPublico";
    String professionCbo = "profissaoCodigoCBO";
    String professionNeoway = "profissaoNeoway";
    String quantityQsaUnique = "quantidadeEmpresasParticipacaoQsaUnico";
    String sicaf = "sicaf";
    //Level 2
    String number = "numero";
    String neighborhood = "bairro";
    String zip = "cep";
    String address2 = "complemento";
    String address1 = "logradouro";
    String municipality = "municipio";
    String uf = "uf";
    String situation = "situacao";
    String registerSituation = "situacaoCadastral";

    default Person bind(Person person, Map source) {
        setDefaultData(person, source);

        var phonesLevel1 = source.get(phones) == null
                ? null
                : (List<Map>) source.get(phones);
        if (phonesLevel1 != null) {
            person.getPhones().clear();
            person.getPhones().addAll(phonesLevel1
                    .stream()
                    .map(map -> map.get(number) == null
                            ? null
                            : map.get(number).toString())
                    .filter(Objects::nonNull)
                    .toList());
        }


        if (source.get(address) != null)
            person.setMainAddress(getAddress((Map) source.get(address)));

        var addressesLevel2 = source.get(addresses) == null
                ? null
                : (List<Map>) source.get(addresses);
        if (addressesLevel2 != null) {
            person.getOtherAddresses().clear();
            person.getOtherAddresses().addAll(addressesLevel2
                    .stream()
                    .map(this::getAddress)
                    .toList());
        }

        if (source.get(sicaf) != null) {
            var sicafLevel1 = (Map) source.get(sicaf);
            person.setSituation(sicafLevel1.get(situation) == null
                    ? null
                    : sicafLevel1.get(situation).toString());
            person.setRegisterSituation(sicafLevel1.get(registerSituation) == null
                    ? null
                    : sicafLevel1.get(registerSituation).toString());
        }

        return person;
    }

    private void setDefaultData(Person person, Map source) {
        person.setCpf(nonNull(source.get(cpf))
                ? source.get(cpf).toString()
                : null);
        person.setName(nonNull(source.get(name))
                ? source.get(name).toString()
                : null);
        person.setSex(nonNull(source.get(sex))
                ? source.get(sex).toString()
                : null);
        person.setCns(nonNull(source.get(cns))
                ? source.get(cns).toString()
                : null);
        person.setNis(nonNull(source.get(nis))
                ? source.get(nis).toString()
                : null);
        person.setEmail(nonNull(source.get(email))
                ? source.get(email).toString()
                : null);
        person.setMotherCpf(nonNull(source.get(motherCpf))
                ? source.get(motherCpf).toString()
                : null);
        person.setMotherName(nonNull(source.get(motherName))
                ? source.get(motherName).toString()
                : null);
        person.setFatherName(nonNull(source.get(fatherName))
                ? source.get(fatherName).toString()
                : null);
        person.setOriginCountry(nonNull(source.get(country))
                ? source.get(country).toString()
                : null);
        person.setSocialInscription(nonNull(source.get(socialInscription))
                ? source.get(socialInscription).toString()
                : null);
        person.setDebitPgfnDauValue(nonNull(source.get(debitPgfnDau))
                ? source.get(debitPgfnDau).toString()
                : null);
        person.setSituationCpf(nonNull(source.get(situationCpf))
                ? source.get(situationCpf).toString()
                : null);
        person.setEducationLevel(nonNull(source.get(educationLevel))
                ? source.get(educationLevel).toString()
                : null);
        person.setAge(nonNull(source.get(age))
                ? parseInt(source.get(age).toString())
                : 0);
        person.setDeadDate(nonNull(source.get(deadDate))
                ? parseInt(source.get(deadDate).toString())
                : 0);
        person.setDeficiency(nonNull(source.get(deficiency)) && (boolean) source.get(deficiency));
        person.setDeficiencyType(nonNull(source.get(deficiencyType))
                ? source.get(deficiencyType).toString()
                : null);
        person.setDeadConfirmation(nonNull(source.get(deadConfirmation)) && (boolean) source.get(deadConfirmation));
        person.setPublicAgent(nonNull(source.get(publicAgent)) && (boolean) source.get(publicAgent));
        person.setProfessionNeoway(nonNull(source.get(professionNeoway))
                ? source.get(professionNeoway).toString()
                : null);
        person.setProfessionCbo(nonNull(source.get(professionCbo))
                ? source.get(professionCbo).toString()
                : null);
        person.setInscriptionCpfDate(nonNull(source.get(inscriptionCpfDate))
                ? source.get(inscriptionCpfDate).toString()
                : null);
        person.setQuantityQsaUnique(nonNull(source.get(quantityQsaUnique))
                ? parseInt(source.get(quantityQsaUnique).toString())
                : 0);

        try {
            person.setBirthDate(nonNull(source.get(birthDate))
                    ? convert(source.get(birthDate).toString())
                    : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
    }

    private Address getAddress(Map source) {
        var address = new Address();
        address.setNeighborhood(nonNull(source.get(neighborhood))
                ? source.get(neighborhood).toString()
                : null);
        address.setZip(nonNull(source.get(zip))
                ? source.get(zip).toString()
                : null);
        address.setAddress1(nonNull(source.get(address1))
                ? source.get(address1).toString()
                : null);
        address.setAddress2(nonNull(source.get(address2))
                ? source.get(address2).toString()
                : null);
        address.setMunicipality(nonNull(source.get(municipality))
                ? source.get(municipality).toString()
                : null);
        address.setUf(nonNull(source.get(uf))
                ? source.get(uf).toString()
                : null);

        return address;
    }

}
