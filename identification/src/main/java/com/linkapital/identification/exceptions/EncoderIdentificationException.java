package com.linkapital.identification.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EncoderIdentificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public EncoderIdentificationException(String message) {
        super(message);
    }

}
