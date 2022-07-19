package com.linkapital.core.util.controller;

import java.io.Serializable;

/**
 * Represents an error occurred within the application. Provides support for useful information transport.
 *
 * @since 0.1.0
 */
public class Error implements Serializable {

    private static final long serialVersionUID = -7515187141251150970L;
    private ErrorCode code;
    private String detail;

    public Error() {
    }

    public Error(ErrorCode code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    /**
     * Build an error based on the given code and detail.
     *
     * @param code   The code of the error.
     * @param detail The detail of the error.
     * @return An error object populated with the specified code and detail.
     */
    static public Error err(ErrorCode code, String detail) {
        return new Error(code, detail);
    }

    //<editor-fold desc="Encapsulation">
    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    //</editor-fold>
}
