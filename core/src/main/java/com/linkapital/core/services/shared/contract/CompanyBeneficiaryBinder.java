package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.CompanyBeneficiary;
import org.mapstruct.Mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.linkapital.core.services.shared.contract.PartnerBinder.dead;
import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface CompanyBeneficiaryBinder {

    //Level 1
    String beneficiary = "beneficiarios";
    String beneficiaryBoard = "beneficiariosJunta";
    String beneficiaryQsa = "beneficiariosQsaUnificado";
    //Level 2
    String document = "documento";
    String name = "nome";
    String grade = "grau";
    String participation = "participacao";

    Function<Map, CompanyBeneficiary> populate = source -> {
        var documentValue = source.get(document) == null
                ? null
                : source.get(document).toString();

        if (!hasText(documentValue))
            return null;

        return new CompanyBeneficiary()
                .withDocument(documentValue)
                .withName(source.get(name) == null
                        ? null
                        : source.get(name).toString())
                .withGrade(source.get(grade) == null
                        ? 0
                        : parseInt(source.get(grade).toString()))
                .withParticipation(source.get(participation) == null
                        ? 0
                        : parseDouble(source.get(participation).toString()))
                .withDead(source.get(dead) != null && parseBoolean(source.get(dead).toString()));
    };

    BiConsumer<Set<CompanyBeneficiary>, List<Map>> updateBeneficiaries = (companyBeneficiaries, source) ->
            source.forEach(map -> {
                var documentValue = map.get(document) == null
                        ? null
                        : map.get(document).toString();
                if (hasText(documentValue)) {
                    companyBeneficiaries
                            .stream()
                            .filter(companyBeneficiary -> documentValue.equals(companyBeneficiary.getDocument()))
                            .findFirst()
                            .ifPresentOrElse(companyBeneficiary -> companyBeneficiary
                                    .withGradeQsa(map.get(grade) == null
                                            ? 0
                                            : parseInt(map.get(grade).toString()))
                                    .withParticipationQsa(map.get(participation) == null
                                            ? 0
                                            : parseDouble(map.get(participation).toString())), () -> {
                                var companyBeneficiary = populate.apply(map);
                                if (companyBeneficiary != null)
                                    companyBeneficiaries.add(companyBeneficiary);
                            });
                }
            });

    BiFunction<Boolean, Map, Set<CompanyBeneficiary>> getCompanyBeneficiaries = (isHasDivergentQSA, source) -> {
        List<Map> beneficiariesLevel1 = null;
        if (source.get(beneficiaryBoard) != null)
            beneficiariesLevel1 = (List<Map>) source.get(beneficiaryBoard);
        else if (source.get(beneficiary) != null)
            beneficiariesLevel1 = (List<Map>) source.get(beneficiary);

        var companyBeneficiaries = new HashSet<CompanyBeneficiary>();
        if (beneficiariesLevel1 != null)
            companyBeneficiaries.addAll(beneficiariesLevel1
                    .stream()
                    .map(populate)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));

        if (TRUE.equals(isHasDivergentQSA) && source.get(beneficiaryQsa) != null)
            updateBeneficiaries.accept(companyBeneficiaries, (List<Map>) source.get(beneficiaryQsa));

        return companyBeneficiaries;
    };

}
