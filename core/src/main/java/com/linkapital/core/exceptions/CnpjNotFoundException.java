package com.linkapital.core.exceptions;


public class CnpjNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CnpjNotFoundException(String message) {
        super(message);
    }

}