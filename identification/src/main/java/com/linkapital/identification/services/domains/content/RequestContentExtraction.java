package com.linkapital.identification.services.domains.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestContentExtraction {

    private String fileBase64;
    private String type;
    private boolean returnImage;

    public RequestContentExtraction withFileBase64(String fileBase64) {
        setFileBase64(fileBase64);
        return this;
    }

    public RequestContentExtraction withType(String type) {
        setType(type);
        return this;
    }

    public RequestContentExtraction withReturnImage(boolean returnImage) {
        setReturnImage(returnImage);
        return this;
    }

}
