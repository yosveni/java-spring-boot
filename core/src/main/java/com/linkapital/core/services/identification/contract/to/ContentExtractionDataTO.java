package com.linkapital.core.services.identification.contract.to;

import com.linkapital.identification.services.domains.content.ContentExtraction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentExtractionDataTO {

    private ContentExtraction front;
    private ContentExtraction back;

}
