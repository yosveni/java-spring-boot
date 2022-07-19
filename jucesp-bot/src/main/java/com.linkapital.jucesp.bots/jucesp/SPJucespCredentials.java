package com.linkapital.jucesp.bots.jucesp;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SPJucespCredentials {

    private String cpf;
    private String password;

}
