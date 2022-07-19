package com.linkapital.identification.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedIdentificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnauthorizedIdentificationException(String message) {
        super(message);
    }

}
