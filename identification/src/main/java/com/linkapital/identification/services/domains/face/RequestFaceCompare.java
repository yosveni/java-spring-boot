package com.linkapital.identification.services.domains.face;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFaceCompare {

    private byte[] faceFileBase64A;
    private byte[] faceFileBase64B;

    public RequestFaceCompare withFaceFileBase64A(byte[] faceFileBase64A) {
        setFaceFileBase64A(faceFileBase64A);
        return this;
    }

    public RequestFaceCompare withFaceFileBase64B(byte[] faceFileBase64B) {
        setFaceFileBase64B(faceFileBase64B);
        return this;
    }

}
