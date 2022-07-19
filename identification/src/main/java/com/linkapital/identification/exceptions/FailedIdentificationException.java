package com.linkapital.identification.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class FailedIdentificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public FailedIdentificationException(String message) {
        super(message);
    }

}
