package com.linkapital.identification.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkapital.identification.exceptions.FailedIdentificationException;
import com.linkapital.identification.exceptions.UnauthorizedIdentificationException;
import com.linkapital.identification.services.domains.content.ContentExtractionInfo;
import org.slf4j.Logger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.HttpClientErrorException;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.slf4j.LoggerFactory.getLogger;

public class ResponseError {

    private static final Logger log = getLogger(ResponseError.class);
    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json()
            .serializationInclusion(NON_EMPTY)
            .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();

    private ResponseError() {
    }

    public static FailedIdentificationException processException(HttpClientErrorException e)
            throws FailedIdentificationException, UnauthorizedIdentificationException {

        var obj = parse(e.getResponseBodyAsString(), ContentExtractionInfo.class);

        if (obj == null)
            throw new FailedIdentificationException(e.getMessage());

        if (e.getStatusCode().value() == 401)
            throw new UnauthorizedIdentificationException(obj.getStatus().getMessage());
        else
            throw new FailedIdentificationException(obj.getStatus().getMessage());
    }

    /**
     * Deserializes the given <i>JSON formatted</i> string into an object whose type is also specified.
     *
     * @param content The <i>JSON formatted</i> string to be deserialized.
     * @param clazz   The class type of the desired object result.
     * @return The result of the deserialization operation, hopefully with the given type, otherwise. <b>null</b> will
     * be returned.
     */
    public static <T> T parse(String content, Class<T> clazz) {
        try {
            return MAPPER.readValue(content, clazz);
        } catch (Exception ex) {
            log.trace("Error detected when trying to parse an object from JSON", ex);
            return null;
        }
    }

}
