package com.linkapital.identification.services.domains.liveness;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLivenessDetection {

    private String file;
    private String[] movements = {"up", "down", "left", "right"};

    public RequestLivenessDetection withFile(String file) {
        setFile(file);
        return this;
    }

}
