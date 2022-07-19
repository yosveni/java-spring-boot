package com.linkapital.core.services.bank_operation.contract.to;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseBodyTO {

    private int numFound;
    private int start;
    private List<BndesResponseTO> docs;

    public ResponseBodyTO() {
        this.docs = new ArrayList<>();
    }

}
