package com.linkapital.identification.services;

import com.linkapital.identification.exceptions.EncoderIdentificationException;
import com.linkapital.identification.exceptions.FailedIdentificationException;
import com.linkapital.identification.exceptions.UnauthorizedIdentificationException;
import com.linkapital.identification.services.domains.content.ContentExtractionInfo;
import com.linkapital.identification.services.domains.face.FaceCompareInfo;
import com.linkapital.identification.services.domains.liveness.LivenessDetectionInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public interface IIdentificationService {

    ContentExtractionInfo contextExtraction(@NotNull MultipartFile file, @NotNull String type) throws FailedIdentificationException, UnauthorizedIdentificationException;

    LivenessDetectionInfo livenessDetection(@NotNull MultipartFile file) throws FailedIdentificationException, UnauthorizedIdentificationException, EncoderIdentificationException;

    FaceCompareInfo faceCompare(@NotNull byte[] base64Document, @NotNull byte[] base64Image) throws FailedIdentificationException, UnauthorizedIdentificationException;

}
