package com.linkapital.core.exceptions;

public class SystemConfigurationException extends Exception {

    private static final long serialVersionUID = 1L;

    public SystemConfigurationException(String message) {
        super(message);
    }

    public SystemConfigurationException(String message, Throwable e) {
        super(message, e);
    }

}
