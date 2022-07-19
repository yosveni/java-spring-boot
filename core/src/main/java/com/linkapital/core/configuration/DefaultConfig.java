package com.linkapital.core.configuration;

import com.linkapital.captcha.EnableCaptcha;
import com.linkapital.identification.configuration.EnableIdentification;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static com.linkapital.core.configuration.context.MessageContextHolder.source;
import static java.util.Arrays.asList;

@Configuration
@Import({
        SwaggerConfig.class,
        DataSourceConfig.class,
        CommonValidationConfig.class,
        ThreadPoolTaskConfig.class,
        SecurityConfig.class,
        WebSocketConfig.class
})
@ComponentScan({
        "com.linkapital.jucesp.bots",
        "com.linkapital.core.resources",
        "com.linkapital.core.events",
        "com.linkapital.core.services.security.jwt",
        "com.linkapital.core.services.*.impl",
        "com.linkapital.core.services.*.validator",
        "com.linkapital.core.exceptions",
        "com.linkapital.core.aop"
})
@EnableIdentification
@EnableCaptcha
public class DefaultConfig {

    @Bean
    public MessageSource messageSource() {
        return source();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
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
