package com.linkapital.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class MaxSizeException extends Exception {

    private static final long serialVersionUID = 1L;

    public MaxSizeException(String message) {
        super(message);
    }

}
