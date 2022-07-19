package com.linkapital.core.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public class JsonSerdes {

    private static final Logger log = getLogger(JsonSerdes.class);
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(NON_EMPTY)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }

    //-- File Operations

    private JsonSerdes() {
        throw new AssertionError(msg("jsonserdes.instance.not.found"));
    }

    //-- Serialization Operations

    /**
     * Transform the given array to a json formatted string.
     *
     * @param array         The array to be convert.
     * @param includeQuotes Flag to specified in the quotes are desired or not for the individual elements.
     * @return A string that represents the specified array in json format.
     */
    public static String jsonfy(String[] array, boolean includeQuotes) {
        var stream = includeQuotes ? stream(array).map(element -> format("\"%s\"", element)) : stream(array);
        return stream.collect(joining(",", "[", "]"));
    }

    /**
     * Serializes the given object to <i>JSON formatted</i> string
     *
     * @param obj The object to be serialized.
     * @return A resultant string that is the json representation of the specified object, <b>null</b> in case of face
     * an unexpected error.
     */
    public static String jsonfy(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception ex) {
            log.trace("Error detected when trying to covert an object to JSON", ex);
            return null;
        }
    }

    //-- Deserialization Operations
    public static byte[] jsonfyAsBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (Exception ex) {
            log.trace("Error detected when trying to covert an object to JSON (as bytes)", ex);
            return new byte[0];
        }
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

    public static <T> T parse(byte[] content, Class<T> clazz) {
        try {
            return MAPPER.readValue(content, clazz);
        } catch (Exception ex) {
            log.trace("Error detected when trying to parse an object from JSON", ex);
            return null;
        }
    }

    /**
     * Deserializes the given <i>JSON formatted</i> string into an object whose type and generic subtype is also
     * specified.
     *
     * @param content The <i>JSON formatted</i> string to be deserialized.
     * @param type    The class type and the parameterized sub type of the desired object result.
     * @return The result of the deserialization operation, hopefully with the given type and parameterized sub type,
     * otherwise. <b>null</b> will be returned.
     */
    public static <T> T parse(String content, TypeReference<?> type) {
        try {
            return (T) MAPPER.readValue(content, type);
        } catch (Exception ex) {
            log.trace("Error detected when trying to parse an object from JSON", ex);
            return null;
        }
    }

    public static <T> T parse(byte[] content, TypeReference<?> type) {
        try {
            return (T) MAPPER.readValue(content, type);
        } catch (Exception ex) {
            log.trace("Error detected when trying to parse an object from JSON", ex);
            return null;
        }
    }

    //-- Conversion Operations
    public static <T> T convert(Object obj, Class<T> type) {
        return MAPPER.convertValue(obj, type);
    }

    public static <T> T convert(Object obj, TypeReference<T> type) {
        return MAPPER.convertValue(obj, type);
    }

    /**
     * Retrieves the mapper used in the utility operations.
     *
     * @return The configured mapper that is used in the utility operations.
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

}
