package com.linkapital.identification.services.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestInfo {

    private long elapsedMilliseconds;
    private HttpStatus status;

}
