package com.linkapital.captcha;

public class CaptchaErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public CaptchaErrorException(String message) {
        super(message);
    }

}
