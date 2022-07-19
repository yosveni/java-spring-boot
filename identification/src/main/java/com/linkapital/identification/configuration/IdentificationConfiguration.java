package com.linkapital.identification.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;

@ComponentScan({
        "com.linkapital.identification.services.impl"
})
@Configuration
public class IdentificationConfiguration {

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getMessageConverters().add(jacksonSupportsMoreTypes());
        return restTemplate;
    }

    private HttpMessageConverter jacksonSupportsMoreTypes() {
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(asList(MediaType.parseMediaType("text/plain;charset=utf-8"),
                MediaType.APPLICATION_OCTET_STREAM));

        return converter;
    }

}
