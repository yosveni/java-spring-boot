package com.linkapital.core.services.industrial_production_index.impl;

import com.linkapital.core.services.industrial_production_index.PhysicalProductionService;
import com.linkapital.core.services.industrial_production_index.contract.to.ResponsePhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.services.industrial_production_index.contract.PhysicalProductionBinder.PHYSICAL_PRODUCTION_BINDER;
import static com.linkapital.core.util.DateUtil.parseToLocalDate;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.springframework.util.StringUtils.hasText;

@Service
public class PhysicalProductionServiceImpl implements PhysicalProductionService {

    private static final String URI_BASE = "https://sidra.ibge.gov.br/Ajax/JSon/Valores/1/3650";
    private final RestTemplate restTemplate;

    public PhysicalProductionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Retry(name = "sidra")
    public List<PhysicalProduction> getPhysicalProduction(String cnae) {
        var requestParams = new LinkedMultiValueMap<String, Object>();
        requestParams.add("params", "t/3650/f/n/h/n/n1/all/V/3135,3136,3137,3138,3139,3140,3141/P/last48/C542/129176," +
                "129177,129178,129179,129180,129181,129182,129183,129184,129185,129186,129187,129188,129189,129190," +
                "32778,129192,129193,129194,129195,129196,129197,129198,129199,129200,32779,129202,129203,31761," +
                "129204,129205,129206,129207,129208,129209,129210,129211,129212,129213,129214,129215,129216,32780," +
                "129217,129218,129219,129220,129221,129222,129223,129225,129226,129227,129228,129229,129230,129231," +
                "129232,129233,129234,129235,129236,129237,129238,129239,129240,129241,129242,129243,129244,129245," +
                "32781,129246,129247,129249,129250,129251,129252,129253,129254,129255,129256,129257,129258,129259," +
                "129260,129261,129262,129263,129264,129265,129266,129267,129268,129269,129270,129271,129272,129273," +
                "129274,129275,129276,129277/d/v3135 1,v3136 1,v3137 1,v3138 1,v3139 1,v3140 1,v3141 1");
        requestParams.add("versao", -1);
        requestParams.add("desidentifica", false);
        List<PhysicalProduction> list = new ArrayList<>();

        var response = restTemplate.postForEntity(URI_BASE, requestParams,
                ResponsePhysicalProductionTO[].class);

        var body = response.getBody();
        if (response.getStatusCode().value() == 200 && body != null)
            list = buildPhysicalProductions(body, cnae);

        return list;
    }

    //region Build physical productions
    private List<PhysicalProduction> buildPhysicalProductions(ResponsePhysicalProductionTO[] data, String cnae) {
        var cnaeNumbers = new AtomicReference<HashSet<String>>(new HashSet<>());

        return stream(data)
                .filter(response -> hasText(response.getDate()) && hasText(response.getCodeDescription()))
                .filter(response -> {
                    var code = splitCodeDescription(response.getCodeDescription());
                    return code.length > 2 && code[1].equals("e")
                            ? rangeClosed(parseInt(code[0]), parseInt(code[2]))
                            .anyMatch(r -> matchCodeCnae(valueOf(r), cnae, cnaeNumbers))
                            : matchCodeCnae(code[0], cnae, cnaeNumbers);
                })
                .map(responsePhysicalProduction -> responsePhysicalProduction
                        .withDate(parseToLocalDate(responsePhysicalProduction.getDate()).toString()))
                .collect(
                        groupingBy(
                                ResponsePhysicalProductionTO::getDate,
                                TreeMap::new,
                                toList()))
                .descendingMap()
                .values()
                .stream()
                .map(PHYSICAL_PRODUCTION_BINDER::bind)
                .filter(Objects::nonNull)
                .toList();
    }
    //endregion

    //region Assert Cnae and D4n matches given certain circunstances
    private boolean matchCodeCnae(@NonNull String codeDescriptionNumbers,
                                  @NonNull String cnae,
                                  AtomicReference<HashSet<String>> cnaeNumbers) {
        var utilCnae = cnae.substring(0, 3);

        if (codeDescriptionNumbers.length() >= 4) {
            var usedCnaeNumbers = cnaeNumbers.get();
            var matched = !usedCnaeNumbers.contains(utilCnae);
            usedCnaeNumbers.add(utilCnae);
            cnaeNumbers.set(usedCnaeNumbers);
            return matched && codeDescriptionNumbers.contains(utilCnae);
        }

        return codeDescriptionNumbers.substring(0, 3).equals(utilCnae);
    }
    //endregion

    //region Split Code Description
    private String @NonNull [] splitCodeDescription(@NonNull String codeDescription) {
        return codeDescription
                .trim()
                .replace(".", "")
                .replace(",", "")
                .split(" ");
    }
    //endregion

}
