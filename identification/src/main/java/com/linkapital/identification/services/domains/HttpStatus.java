package com.linkapital.identification.services.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpStatus {

    private String code;
    private String message;
    private String errors;

}
