package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import com.linkapital.core.services.person.datasource.domain.CorporateParticipation;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.person.datasource.domain.Relationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface PartnerBinder {

    PartnerBinder PARTNER_BINDER = Mappers.getMapper(PartnerBinder.class);
    Logger log = getLogger(PartnerBinder.class);

    //Level 2
    String socialCapital = "capitalSocialEmpresa";
    String cnpj = "cnpj";
    String openDate = "dataAbertura";
    String entryDate = "dataPrimeiraCaptura";
    String descriptionCnae = "descricaoCnae";
    String estimatedBilling = "faixaFaturamentoPresumido";
    String estimatedBillingGroup = "faixaFaturamentoPresumidoGrupo";
    String municipality = "municipio";
    String participationSocialCapital = "participacaoCapitalSocial";
    String qualification = "qualificacao";
    String activity = "ramoAtividade";
    String socialReason = "razaoSocial";
    String situation = "situacao";
    String uf = "uf";
    String document = "documento";
    String dead = "falecido";
    String originCountry = "paisOrigem";
    String name = "nome";
    String participation = "valorParticipacao";
    String partnerParticipation = "participacaoSocietaria";
    String levelPreparation = "nivelPep";
    String criminalProcess = "categoriaProcessoCriminal";
    String cpf = "cpf";
    String description = "descricao";

    Function<Map, CorporateParticipation> buildCorporateParticipation = source -> new CorporateParticipation()
            .withCnpj(source.get(cnpj) == null
                    ? null
                    : source.get(cnpj).toString())

            .withSocialReason(source.get(socialReason) == null
                    ? null
                    : source.get(socialReason).toString())

            .withDescriptionCnae(source.get(descriptionCnae) == null
                    ? null
                    : source.get(descriptionCnae).toString())

            .withBusinessActivityCnae(source.get(activity) == null
                    ? null
                    : source.get(activity).toString())

            .withSocialCapital(source.get(socialCapital) == null
                    ? null
                    : source.get(socialCapital).toString())

            .withSituation(source.get(situation) == null
                    ? null
                    : source.get(situation).toString())

            .withEstimatedBilling(source.get(estimatedBilling) == null
                    ? null
                    : source.get(estimatedBilling).toString())

            .withEstimatedBillingGroup(source.get(estimatedBillingGroup) == null
                    ? null
                    : source.get(estimatedBillingGroup).toString())

            .withMunicipality(source.get(municipality) == null
                    ? null
                    : source.get(municipality).toString())

            .withUf(source.get(uf) == null
                    ? null
                    : source.get(uf).toString())

            .withOpeningDate(source.get(openDate) == null
                    ? null
                    : LocalDateTime.parse(source.get(openDate).toString(), ISO_ZONED_DATE_TIME))

            .withQualification(source.get(qualification) == null
                    ? null
                    : source.get(qualification).toString())

            .withLevelPreparation(source.get(levelPreparation) == null
                    ? null
                    : source.get(levelPreparation).toString())

            .withParticipation(source.get(participation) == null
                    ? 0
                    : parseDouble(source.get(participation).toString()))

            .withParticipationSocialCapital(source.get(participationSocialCapital) == null
                    ? 0
                    : parseDouble(source.get(participationSocialCapital).toString()));

    BiConsumer<List<Map>, Set<CorporateParticipation>> updateCorporateParticipationRF = (source, data) ->
            source.forEach(map -> {
                var document = map.get(cnpj) == null
                        ? null
                        : map.get(cnpj).toString();
                if (hasText(document)) {
                    data
                            .stream()
                            .filter(corporateParticipation -> corporateParticipation.getCnpj().equals(document))
                            .findFirst()
                            .ifPresentOrElse(corporateParticipation -> corporateParticipation
                                    .withQualificationRF(map.get(qualification) == null
                                            ? null
                                            : map.get(qualification).toString())
                                    .withLevelPreparationRF(map.get(levelPreparation) == null
                                            ? null
                                            : map.get(levelPreparation).toString())
                                    .withParticipationRF(map.get(participation) == null
                                            ? 0
                                            : parseDouble(map.get(participation).toString()))
                                    .withParticipationSocialCapitalRF(map.get(participationSocialCapital) == null
                                            ? 0
                                            : parseDouble(map.get(participationSocialCapital).toString())), () ->
                                    data.add(buildCorporateParticipation.apply(map)));
                }
            });

    Function<List<Map>, Set<Relationship>> bindRelationships = source -> source
            .stream()
            .map(map -> new Relationship()
                    .withCpf(map.get(cpf) == null
                            ? null
                            : map.get(cpf).toString())
                    .withName(map.get(name) == null
                            ? null
                            : map.get(name).toString())
                    .withDescription(map.get(description) == null
                            ? null
                            : map.get(description).toString()))
            .collect(toSet());

    default Company buildCompanyForCompanyPartners(Map source) {
        var company = new Company();
        company.setOriginCountry(source.get(originCountry) == null
                ? null
                : source.get(originCountry).toString());
        company.setMainInfo(getMainInfoFromCompanyPartner(source));

        return company;
    }

    default CompanyPartners bindDefaultCompanyPartners(Company company, Map source) {
        var companyPartners = new CompanyPartners();
        companyPartners.setQualification(nonNull(source.get(qualification))
                ? source.get(qualification).toString()
                : null);
        companyPartners.setLevelPreparation(nonNull(source.get(levelPreparation))
                ? source.get(levelPreparation).toString()
                : null);
        companyPartners.setParticipation(nonNull(source.get(partnerParticipation))
                ? parseDouble(source.get(partnerParticipation).toString())
                : 0);
        companyPartners.setCompany(company);

        return companyPartners;
    }

    default void updateCompanyPartners(List<CompanyPartners> companyPartners, List<Map> source) {
        var findMap = new AtomicReference<Optional<Map>>();
        companyPartners.forEach(companyPartner -> {
            Optional
                    .ofNullable(companyPartner.getPerson())
                    .ifPresentOrElse(person ->
                            findMap.set(source
                                    .stream()
                                    .filter(map -> nonNull(map.get(name))
                                            && map.get(name).toString().equals(person.getName()))
                                    .findFirst()), () ->
                            findMap.set(source
                                    .stream()
                                    .filter(map -> nonNull(map.get(name))
                                            && map.get(name).toString().equals(
                                            companyPartner.getCompany().getMainInfo().getSocialReason()))
                                    .findFirst())
                    );

            findMap.get()
                    .ifPresent(map -> {
                        companyPartner.setLevelPreparationRF(nonNull(map.get(levelPreparation))
                                ? map.get(levelPreparation).toString()
                                : null);
                        companyPartner.setQualificationRF(nonNull(map.get(qualification))
                                ? map.get(qualification).toString()
                                : null);
                        companyPartner.setParticipationRF(nonNull(map.get(participation))
                                ? parseDouble(map.get(participation).toString())
                                : 0);

                        if (nonNull(companyPartner.getPerson()))
                            companyPartner.getPerson().setCriminalProcess(nonNull(map.get(criminalProcess))
                                    ? ((List<String>) map.get(criminalProcess)).stream().collect(toSet())
                                    : null);
                    });
        });
    }

    default Person bindDefaultPerson(Map source) {
        var person = new Person();
        person.setName(nonNull(source.get(name)) ? source.get(name).toString() : null);
        person.setCpf(nonNull(source.get(document)) ? source.get(document).toString() : null);
        person.setOriginCountry(nonNull(source.get(originCountry)) ? source.get(originCountry).toString() : null);
        person.setDead(nonNull(source.get(dead)) && (boolean) source.get(dead));

        return person;
    }

    private CompanyMainInfo getMainInfoFromCompanyPartner(Map source) {
        var companyInfo = new CompanyMainInfo();
        companyInfo.setCnpj(source.get(document) == null
                ? null
                : source.get(document).toString());
        companyInfo.setSocialReason(source.get(name) == null
                ? null
                : source.get(name).toString());

        return companyInfo;
    }

}
