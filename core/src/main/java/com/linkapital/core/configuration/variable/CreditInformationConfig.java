package com.linkapital.core.configuration.variable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class CreditInformationConfig {

    private final String host;
    private final String dataSource;
    private final String token;
    private final String xmlInput;
    private final int clientType;

    public CreditInformationConfig(@Value("${api_src.host}") String host,
                                   @Value("${data-source-credit-information}") String dataSource,
                                   @Value("${api_src.token}") String token,
                                   @Value("${api_src.input}") String xmlInput,
                                   @Value("${api_src.client_type}") int clientType) {
        this.host = host;
        this.dataSource = dataSource;
        this.token = token;
        this.xmlInput = xmlInput;
        this.clientType = clientType;
    }

}
