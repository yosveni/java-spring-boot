package com.linkapital.identification.services.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String token;

    public Token withToken(String token) {
        setToken(token);
        return this;
    }

}
