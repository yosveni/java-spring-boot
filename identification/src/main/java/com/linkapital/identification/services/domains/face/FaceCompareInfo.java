package com.linkapital.identification.services.domains.face;

import com.linkapital.identification.services.domains.RequestInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaceCompareInfo extends RequestInfo {

    private String queryId;
    private FaceCompare result;

}
