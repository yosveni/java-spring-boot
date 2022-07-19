package com.linkapital.core.services.protest.impl;

import com.linkapital.core.exceptions.StorageException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.protest.ProtestService;
import com.linkapital.core.services.protest.contract.to.response.ResponseProtestDataTO;
import com.linkapital.core.services.protest.datasource.domain.ProtestInformation;
import com.linkapital.core.services.storage.StorageService;
import lombok.NonNull;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.protest.contract.ProtestBinder.PROTEST_BINDER;
import static com.linkapital.core.util.json.JsonSerdes.jsonfy;
import static com.linkapital.core.util.json.JsonSerdes.parse;
import static java.lang.String.format;

@Service
public class ProtestServiceImpl implements ProtestService {

    private static final String protestEndpoint = "/api/v2/consultas/ieptb/protestos";
    private final Logger log = LoggerFactory.getLogger(ProtestServiceImpl.class);
    private final String host;
    private final String token;
    private final String dataSource;
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final StorageService storageService;

    public ProtestServiceImpl(@Value("${api_integration.infosimples.host}") String host,
                              @Value("${api_integration.infosimples.token}") String token,
                              @Value("${data-source-protest}") String dataSource,
                              RestTemplate restTemplate,
                              ResourceLoader resourceLoader,
                              StorageService storageService) {
        this.host = host;
        this.token = token;
        this.dataSource = dataSource;
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.storageService = storageService;
    }

    @Override
    public ProtestInformation getProtestInformation(String document) throws UnprocessableEntityException {
        ProtestInformation protestInformation = null;
        switch (dataSource) {
            case "API":
                protestInformation = getProtestFromApi(document);
                break;
            case "MOCK":
                try {
                    var data = (Map) new JSONParser(resourceLoader.getResource(
                            format("classpath:mock_protest/protest.json", document)).getInputStream()).anything();
                    protestInformation = processData(data);
                } catch (ParseException | IOException e) {
                    log.error(e.getMessage());
                }
                break;
            default:
                try {
                    var data = storageService.getFileToMap(format("/protest/%s.json", document));
                    protestInformation = processData(data);
                } catch (StorageException e) {
                    log.error(e.getMessage());
                }
        }

        return protestInformation;
    }

    private @NonNull LinkedHashMap<String, String> getParams(@NonNull String document) {
        var documentText = document.length() > 11
                ? "cnpj"
                : "cpf";
        var params = new LinkedHashMap<String, String>();
        params.put("token", token);
        params.put(documentText, document);

        return params;
    }

    private @Nullable ProtestInformation getProtestFromApi(String document) throws UnprocessableEntityException {
        var params = getParams(document);
        var response = restTemplate.postForEntity(
                host + protestEndpoint,
                new HttpEntity<>(params),
                Map.class);

        var body = response.getBody();
        if (response.getStatusCode().value() != 200 && body == null)
            return null;

        var code = (int) body.getOrDefault("code", 0);
        if (code != 200)
            throw new UnprocessableEntityException(body.getOrDefault("code_message", "ERROR")
                    .toString());
        //Todo buscar si existe codigo referente a saldo agotado si existe y el error es referente al saldo, enviar email a los admins

        storageService.storeJsonNeoWay("protests/" + document, body);

        return processData(body);
    }

    private @Nullable ProtestInformation processData(@NonNull Map<String, Object> source) {
        var data = (List<Map<String, Object>>) source.get("data");
        var map = data == null || data.isEmpty()
                ? null
                : data.get(0);
        var toJson = jsonfy(map);
        var responseProtestData = parse(toJson, ResponseProtestDataTO.class);

        return responseProtestData == null
                ? null
                : PROTEST_BINDER.bind(responseProtestData);
    }

}
