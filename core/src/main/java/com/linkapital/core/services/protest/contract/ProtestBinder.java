package com.linkapital.core.services.protest.contract;

import com.linkapital.core.services.protest.contract.emuns.ProtestArea;
import com.linkapital.core.services.protest.contract.to.ProtestAnalysisTO;
import com.linkapital.core.services.protest.contract.to.ProtestAreaTO;
import com.linkapital.core.services.protest.contract.to.ProtestEvolutionTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.protest.contract.to.ProtestLightTO;
import com.linkapital.core.services.protest.contract.to.response.ResponseProtestDataTO;
import com.linkapital.core.services.protest.contract.to.response.ResponseProtestRegistryTO;
import com.linkapital.core.services.protest.contract.to.response.ResponseProtestTO;
import com.linkapital.core.services.protest.datasource.domain.Protest;
import com.linkapital.core.services.protest.datasource.domain.ProtestInformation;
import com.linkapital.core.services.protest.datasource.domain.ProtestRegistry;
import com.linkapital.core.util.json.JsonSerdes;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.linkapital.core.services.protest.contract.emuns.ProtestArea.FINANCIAL_SEGMENT;
import static com.linkapital.core.services.protest.contract.emuns.ProtestArea.OTHER;
import static com.linkapital.core.services.protest.contract.emuns.ProtestArea.TAXES_PUBLIC;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static com.linkapital.core.util.json.JsonSerdes.jsonfy;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.List.of;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.range;

@Mapper
public interface ProtestBinder {

    ProtestBinder PROTEST_BINDER = Mappers.getMapper(ProtestBinder.class);
    List<String> financialSegment = of("investimento, banco, financeira, financeiro, fidc, seguradora," +
            " securitizadora");
    List<String> taxesPublic = of("prefeitura, governo, federal, estado, municipal, municipio, fazenda," +
            " secretaria, procuradoria");

    Function<String, ProtestArea> parseArea = source -> {
        var presenterNameElements = Arrays
                .stream(source.split(" "))
                .map(String::toLowerCase)
                .toList();

        if (financialSegment.stream().anyMatch(presenterNameElements::contains))
            return FINANCIAL_SEGMENT;
        if (taxesPublic.stream().anyMatch(presenterNameElements::contains))
            return TAXES_PUBLIC;
        else
            return OTHER;
    };

    Function<@NonNull List<ResponseProtestTO>, Set<Protest>> buildProtest = source -> source
            .stream()
            .filter(protest -> protest.getValor() != null)
            .map(to -> new Protest()
                    .withKey(to.getChave())
                    .withPresenterName(to.getNome_apresentante())
                    .withAssignorName(to.getNome_cedente())
                    .withValue(to.getValor())
                    .withHasConsent("Sim".equals(to.getTem_anuencia()))
                    .withConsultDate(to.getData_protesto() == null
                            ? null
                            : parse(to.getData_protesto(), ISO_LOCAL_DATE))
                    .withDueDate(to.getData_vencimento() == null
                            ? null
                            : parse(to.getData_vencimento(), ISO_LOCAL_DATE))
                    .withArea(to.getNome_apresentante() == null
                            ? OTHER
                            : parseArea.apply(to.getNome_apresentante())))
            .collect(toSet());

    BiFunction<String, @NonNull List<ResponseProtestRegistryTO>, @NonNull Stream<ProtestRegistry>> buildProtestRegistry
            = (uf, source) ->
            source
                    .stream()
                    .map(to -> new ProtestRegistry()
                            .withName(to.getNome())
                            .withPhone(to.getTelefone())
                            .withAddress(to.getEndereco())
                            .withCityCode(to.getCidade_codigo())
                            .withCityCodeIbge(to.getCidade_codigo_ibge())
                            .withMunicipality(to.getMunicipio())
                            .withCity(to.getCidade())
                            .withNeighborhood(to.getBairro())
                            .withUf(uf)
                            .withCode(to.getCodigo())
                            .withAmount(to.getQuantidade())
                            .withSearchPeriod(to.getPeriodo_pesquisa())
                            .withUpdateDate(to.getAtualizacao_data())
                            .withProtests(buildProtest.apply(to.getProtestos())));

    Function<List<Protest>, List<ProtestAreaTO>> buildProtestArea = source ->
            source
                    .stream()
                    .collect(groupingBy(Protest::getArea))
                    .entrySet()
                    .stream()
                    .map(x -> {
                        var protests = x.getValue();
                        return new ProtestAreaTO()
                                .withPercent(format("%s%%", protests.size() * 100 / source.size()))
                                .withValue(protests
                                        .stream()
                                        .mapToDouble(Protest::getValue)
                                        .sum())
                                .withAmount(protests.size())
                                .withArea(x.getKey());
                    })
                    .toList();

    Function<@NonNull LinkedHashMap<String, Double>, List<ProtestEvolutionTO>> buildProtestEvolution = source -> {
        var result = new ArrayList<ProtestEvolutionTO>();
        Double nextValue;
        Double currentValue;
        Map.Entry<String, Double> currentProtest;
        var valuesIterator = source.values()
                .stream()
                .filter(x -> x != 0)
                .toList()
                .iterator();

        if (valuesIterator.hasNext()) {
            valuesIterator.next();

            String percent;
            for (Map.Entry<String, Double> stringDoubleEntry : source.entrySet()) {
                currentProtest = stringDoubleEntry;
                currentValue = currentProtest.getValue();

                if (currentValue != 0 && valuesIterator.hasNext()) {
                    nextValue = valuesIterator.next();
                    percent = format("%s%%", round((currentValue - nextValue) * 100 / nextValue));
                } else
                    percent = "-%";

                var protestEvolution = new ProtestEvolutionTO()
                        .withDate(currentProtest.getKey())
                        .withValue(currentValue != 0
                                ? currentValue.toString()
                                : "-")
                        .withPercent(percent);
                result.add(protestEvolution);
            }
        }

        return result;
    };

    default ProtestInformation bind(@NonNull ResponseProtestDataTO source) {
        var registry = source.getCartorios();
        if (registry == null || registry.isEmpty())
            return null;

        var registries = registry
                .entrySet()
                .stream()
                .flatMap(entry -> buildProtestRegistry.apply(entry.getKey(), entry.getValue()))
                .collect(toSet());
        var analysis = buildAnalysis(registries);

        return new ProtestInformation()
                .withDocument(source.getDocumento_pesquisado())
                .withTotal(analysis.getAmountProtest())
                .withTotalError(source.getQuantidade_titulos() - analysis.getAmountProtest())
                .withProtestRegistries(registries)
                .withAnalysis(jsonfy(analysis));
    }

    default ProtestAnalysisTO buildAnalysis(@NonNull Set<ProtestRegistry> source) {
        var allProtests = source
                .stream()
                .flatMap(x -> x.getProtests().stream())
                .filter(x -> x.getConsultDate() != null)
                .sorted(comparing(Protest::getConsultDate))
                .toList();

        var totalValue = allProtests
                .stream()
                .mapToDouble(Protest::getValue)
                .sum();

        var firstProtest = min(allProtests, comparing(Protest::getConsultDate));

        var higherValueProtest = max(allProtests, comparing(Protest::getValue));

        var activesByState = source
                .stream()
                .collect(groupingBy(ProtestRegistry::getUf, summingLong(x -> x.getProtests().size())));

        var activesByYear = allProtests
                .stream()
                .collect(groupingBy(x -> valueOf(x.getConsultDate().getYear()), counting()));

        var allProtestPlusEmptyDates = new ArrayList<Protest>();
        if (!allProtests.isEmpty()) {
            var firstDate = allProtests.get(0).getConsultDate();
            var listOfDates = range(0, MONTHS.between(firstDate, now()))
                    .mapToObj(firstDate::plusMonths)
                    .map(x -> new Protest().withConsultDate(x))
                    .toList();
            allProtestPlusEmptyDates.addAll(listOfDates);
            allProtestPlusEmptyDates.addAll(allProtests);
        }

        var locale = new Locale("pt", "BR");

        var allProtestsByMonth = allProtestPlusEmptyDates
                .stream()
                .collect(groupingBy(protest -> protest
                                .getConsultDate()
                                .format(ofPattern("LLL yyyy").withLocale(locale)),
                        summingDouble(Protest::getValue)))
                .entrySet()
                .stream()
                .sorted(comparing(x -> parse(format("%s 01", x.getKey()), ofPattern("LLL yyyy dd").withLocale(locale)),
                        reverseOrder()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        var allProtestsYear = allProtestPlusEmptyDates
                .stream()
                .filter(protest -> protest.getConsultDate() != null)
                .collect(groupingBy(protest -> valueOf(protest.getConsultDate().getYear()), TreeMap::new,
                        summingDouble(Protest::getValue)))
                .descendingMap()
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return new ProtestAnalysisTO()
                .withAmountRegistry(source.size())
                .withAmountProtest(allProtests.size())
                .withTotalValue(totalValue)
                .withFirstProtest(bind(firstProtest))
                .withHigherValueProtest(bind(higherValueProtest))
                .withActivesByState(activesByState)
                .withActivesByYear(activesByYear)
                .withProtestEvolutionByMonths(buildProtestEvolution.apply(allProtestsByMonth))
                .withProtestEvolutionByYears(buildProtestEvolution.apply(allProtestsYear))
                .withProtestsByArea(buildProtestArea.apply(allProtests));
    }

    ProtestLightTO bind(Protest source);

    default ProtestAnalysisTO getAnalysis(Object source) {
        if (source == null)
            return null;

        return source instanceof String
                ? JsonSerdes.parse(source.toString(), ProtestAnalysisTO.class)
                : convert(source, ProtestAnalysisTO.class);
    }

    @Mapping(target = "analysis", expression = "java(source == null ? null : getAnalysis(source.getAnalysis()))")
    ProtestInformationTO bind(ProtestInformation source);

}
