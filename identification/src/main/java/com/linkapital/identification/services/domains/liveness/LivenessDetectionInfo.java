package com.linkapital.identification.services.domains.liveness;

import com.linkapital.identification.services.domains.RequestInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivenessDetectionInfo extends RequestInfo {

    private String requestId;
    private LivenessDetection result;

}
