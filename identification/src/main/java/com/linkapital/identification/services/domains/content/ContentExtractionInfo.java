package com.linkapital.identification.services.domains.content;

import com.linkapital.identification.services.domains.RequestInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ContentExtractionInfo extends RequestInfo implements Serializable {

    private String requestId;
    private List<ContentExtraction> result;

    public ContentExtractionInfo() {
        this.result = new ArrayList<>();
    }

}
