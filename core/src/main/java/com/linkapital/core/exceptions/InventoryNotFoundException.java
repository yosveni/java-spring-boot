package com.linkapital.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InventoryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public InventoryNotFoundException(String message) {
        super(message);
    }

}
