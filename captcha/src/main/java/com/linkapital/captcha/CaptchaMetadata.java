package com.linkapital.captcha;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CaptchaMetadata {

    private String text;
    private int id;

}
