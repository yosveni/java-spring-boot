package com.linkapital.core.services.integrations.impl;


import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.CpfNotFoundException;
import com.linkapital.core.exceptions.NeowayException;
import com.linkapital.core.exceptions.StorageException;
import com.linkapital.core.services.integrations.NeoWaySearchService;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.storage.StorageService;
import lombok.NonNull;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.notification.contract.enums.EmailType.ALERT;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.LOCKED;

@Service
public class NeoWaySearchServiceImpl implements NeoWaySearchService {

    private static final String LOGIN_ENDPOINT = "auth/token";
    private static final String COMPANY_DATA_ENDPOINT = "v1/data/empresas";
    private static final String PERSON_DATA_ENDPOINT = "v1/data/pessoas";
    private final String host;
    private final String applicationName;
    private final String applicationSecret;
    private final String dataSource;
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final StorageService storageService;
    private final EmailService emailService;


    public NeoWaySearchServiceImpl(
            @Value("${api_integration.neo_way.host}") String host,
            @Value("${api_integration.neo_way.application}") String applicationName,
            @Value("${api_integration.neo_way.application_secret}") String applicationSecret,
            @Value("${data-source-neo-way}") String dataSource,
            RestTemplate restTemplate,
            ResourceLoader resourceLoader,
            StorageService storageService,
            EmailService emailService) {
        this.host = host;
        this.applicationName = applicationName;
        this.applicationSecret = applicationSecret;
        this.dataSource = dataSource;
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.storageService = storageService;
        this.emailService = emailService;
    }

    @Override
    public Map getCnpjData(String cnpj) throws CnpjNotFoundException {
        switch (dataSource) {
            case "API":
                var entity = bindHttpEntity();
                try {
                    return getMapNeoWayFromApi(cnpj, entity.getHeaders(), entity, COMPANY_DATA_ENDPOINT);
                } catch (Exception e) {
                    throw new CnpjNotFoundException(e.getLocalizedMessage());
                }
            case "MOCK":
                try {
                    return (Map) new JSONParser(resourceLoader.getResource(format("classpath:mock_cnpj/%s.json", cnpj))
                            .getInputStream()).anything();
                } catch (ParseException | IOException e) {
                    throw new CnpjNotFoundException(e.getMessage());
                }
            default:
                try {
                    return storageService.getFileToMap(format("%s.json", cnpj));
                } catch (StorageException e) {
                    throw new CnpjNotFoundException(e.getMessage());
                }
        }
    }

    @Override
    public Map getCpfData(String cpf) throws CpfNotFoundException {
        switch (dataSource) {
            case "API":
                var entity = bindHttpEntity();
                try {
                    return getMapNeoWayFromApi(cpf, entity.getHeaders(), entity, PERSON_DATA_ENDPOINT);
                } catch (Exception e) {
                    throw new CpfNotFoundException(e.getMessage());
                }
            case "MOCK":
                try {
                    return (Map) new JSONParser(resourceLoader.getResource(format("classpath:mock_cpf/%s.json",
                            cpf)).getInputStream()).anything();
                } catch (IOException | ParseException e) {
                    throw new CpfNotFoundException(e.getMessage());
                }
            default:
                try {
                    return storageService.getFileToMap(format("%s.json", cpf));
                } catch (StorageException e) {
                    throw new CpfNotFoundException(e.getMessage());
                }
        }
    }

    private HttpEntity<String> bindHttpEntity() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var params = format("{\"application\":\"%s\",\"application_secret\":\"%s\"}", applicationName,
                applicationSecret);

        return new HttpEntity<>(params, headers);
    }

    private @NonNull Map getMapNeoWayFromApi(String id,
                                             HttpHeaders headers,
                                             HttpEntity<String> entity,
                                             String endpoint) throws NeowayException, CpfNotFoundException {
        var responseEntity = restTemplate.exchange(format("%s/%s", host, LOGIN_ENDPOINT),
                HttpMethod.POST, entity, new ParameterizedTypeReference<Object>() {
                });
        headers = HttpHeaders.writableHttpHeaders(headers);
        headers.set("Authorization", ((Map) requireNonNull(responseEntity.getBody())).get("token").toString());
        entity = new HttpEntity<>("", headers);

        try {
            responseEntity = restTemplate.exchange(format("%s/%s/%s", host, endpoint, id),
                    HttpMethod.GET, entity, new ParameterizedTypeReference<Object>() {
                    });
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException httpClient && httpClient.getStatusCode() == LOCKED) {
                var msg = msg("neoway.balance.expired");
                emailService.send(ALERT, msg("neoway.alert"), msg);
                throw new NeowayException(msg);
            }

            throw new NeowayException(e.getMessage());
        }

        var map = (Map) responseEntity.getBody();
        if (map == null)
            throw new CpfNotFoundException(msg("company.cnpj.info.not.found.by.ia"));
        storageService.storeJsonNeoWay(id, map);

        return map;
    }

}
