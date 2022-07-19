package com.linkapital.core.services.ibge.contract.to;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IbgeIndicatorResponseTO {

    private long id;
    private List<IbgeIndicatorResResponseTO> res;

    public IbgeIndicatorResponseTO() {
        this.res = new ArrayList<>();
    }

}
