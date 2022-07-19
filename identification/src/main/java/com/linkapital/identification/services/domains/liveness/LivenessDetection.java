package com.linkapital.identification.services.domains.liveness;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivenessDetection {

    private double globalScore;
    private double movementScore;
    private double livenessScore;
    private byte[] frontalImage;

}
