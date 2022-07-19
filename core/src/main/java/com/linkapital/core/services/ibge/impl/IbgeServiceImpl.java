package com.linkapital.core.services.ibge.impl;

import com.linkapital.core.services.ibge.IbgeService;
import com.linkapital.core.services.ibge.contract.to.IbgeIndicatorResponseTO;
import com.linkapital.core.services.ibge.contract.to.IbgeMunicipalityResponseTO;
import com.linkapital.core.services.ibge.datasource.domain.Ibge;
import io.github.resilience4j.retry.annotation.Retry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.linkapital.core.services.ibge.contract.IbgeBinder.buildIbge;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.springframework.util.StringUtils.hasText;

@Service
public class IbgeServiceImpl implements IbgeService {

    private static final String uriBase = "https://servicodados.ibge.gov.br/api/v1/";
    private static final String uriMunicipality = uriBase + "localidades/estados/%s/municipios";
    private static final String uriIndicators = uriBase + "pesquisas/indicadores/25207|28141|29168|29171|29749|29763|" +
            "29765|30255|47001|60036|60037|60048/resultados/%s";
    private final Logger log = LoggerFactory.getLogger(IbgeServiceImpl.class);
    private final RestTemplate restTemplate;

    public IbgeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Ibge getIbge(String uf, String municipality) {
        if (!hasText(uf) || !hasText(municipality))
            return null;
        try {
            var id = getMunicipalityId(uf, municipality);
            if (id == null)
                return null;

            var indicators = getIndicators(id);
            if (indicators.isEmpty())
                return null;

            return buildIbge.apply(indicators);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    private @Nullable Long getMunicipalityId(String uf, String municipality) {
        var collator = Collator.getInstance(new Locale("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);

        var response = restTemplate.getForEntity(
                format(uriMunicipality, uf), IbgeMunicipalityResponseTO[].class);

        return response.getBody() != null && response.getStatusCodeValue() == 200
                ? Arrays
                .stream(response.getBody())
                .filter(to -> collator.compare(municipality, to.getNome()) == 0)
                .findFirst()
                .map(IbgeMunicipalityResponseTO::getId)
                .orElse(null)
                : null;
    }

    @Retry(name = "ibge")
    private List<IbgeIndicatorResponseTO> getIndicators(long id) {
        var response = restTemplate.getForEntity(
                format(uriIndicators, id), IbgeIndicatorResponseTO[].class);

        return response.getBody() != null && response.getStatusCodeValue() == 200
                ? Arrays
                .stream(response.getBody())
                .toList()
                : emptyList();
    }

}
