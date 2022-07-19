package com.linkapital.core.services.identification.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.contract.enums.DocumentRegion;
import com.linkapital.core.services.identification.contract.enums.DocumentType;
import com.linkapital.core.services.identification.datasource.domain.Identification;
import com.linkapital.identification.services.domains.content.ContentExtraction;
import com.linkapital.identification.services.domains.content.ContentExtractionInfo;
import com.linkapital.identification.services.domains.content.ScoreData;
import com.linkapital.identification.services.domains.face.FaceCompare;
import com.linkapital.identification.services.domains.liveness.LivenessDetectionInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.identification.contract.enums.DocumentType.CNH;
import static com.linkapital.core.services.identification.contract.enums.DocumentType.RG;
import static com.linkapital.core.services.identification.contract.enums.IdentificationState.FACE;
import static com.linkapital.core.services.identification.impl.IdentificationServiceImpl.FORMAT;
import static com.linkapital.core.util.DateUtil.calculateDays;
import static org.springframework.util.StringUtils.hasText;

public class IdentificationValidator {

    private static final String msgError = "identification.date.validate.not.valid";

    private static final BiPredicate<DocumentType, String> validateDocumentType = (type, typeReceived) -> {
        if (type == null || !hasText(typeReceived))
            return false;

        var value = switch (typeReceived) {
            case "subtype=rg" -> RG;
            case "subtype=cnh" -> CNH;
            default -> null;
        };

        return value != null && value.equals(type);
    };

    private IdentificationValidator() {
    }

    public static void validateContentExtractionInfo(ContentExtractionInfo data) throws UnprocessableEntityException {
        if (data == null || data.getResult().isEmpty())
            throw new UnprocessableEntityException(msg("identification.image.not.valid"));
    }

    public static Date getValidDate(List<ScoreData> source) throws UnprocessableEntityException {
        var data = source
                .stream()
                .filter(scoreData -> scoreData.getName().equals("data_validade"))
                .findFirst()
                .orElse(null);

        if (data == null || !hasText(data.getValue()) || data.getScore() <= 0.7)
            throw new UnprocessableEntityException(msg(msgError));

        try {
            var date = new SimpleDateFormat(FORMAT).parse(data.getValue().replace("-", ""));
            var diff = calculateDays(new Date(), date);
            if (diff >= 0)
                throw new UnprocessableEntityException(msg(msgError));

            return date;
        } catch (ParseException e) {
            throw new UnprocessableEntityException(msg(msgError));
        }
    }

    public static void validateContextExtraction(ContentExtraction result,
                                                 DocumentType type,
                                                 DocumentRegion region) throws UnprocessableEntityException {
        if (result == null)
            throw new UnprocessableEntityException(msg("identification.document.part.not.found", region.name()));

        var typeDocument = result.getTags()
                .stream()
                .filter(s -> s.startsWith("subtype"))
                .findFirst()
                .orElse(null);

        if (!validateDocumentType.test(type, typeDocument))
            throw new UnprocessableEntityException(msg("identification.document.type.error"));
        else if (result.getImage() == null || result.getScore() <= 0.8)
            throw new UnprocessableEntityException(msg("identification.image.not.valid"));
    }

    public static void validateLivenessDetection(LivenessDetectionInfo livenessDetectionInfo)
            throws UnprocessableEntityException {
        if (livenessDetectionInfo == null || livenessDetectionInfo.getResult() == null)
            throw new UnprocessableEntityException(msg("identification.image.video.error"));

        var result = livenessDetectionInfo.getResult();
        if (result.getLivenessScore() != 1 ||
                result.getMovementScore() < 0.5 ||
                result.getGlobalScore() < 0.5)
            throw new UnprocessableEntityException(msg("identification.image.video.not.valid"));
    }

    public static void validateFaceCompare(FaceCompare result) throws UnprocessableEntityException {
        if (result == null || result.getDistances().isEmpty() || result.getDistances().get(0) >= 0.5)
            throw new UnprocessableEntityException(msg("identification.image.document.selfie.not.valid"));
    }

    public static void validateObjectIsNull(Object obj, String msg) throws UnprocessableEntityException {
        if (obj == null)
            throw new UnprocessableEntityException(msg);
    }

    public static void validate(Identification identification) throws UnprocessableEntityException {
        if (identification != null && identification.getState().equals(FACE))
            throw new UnprocessableEntityException(msg("identification.already.exist"));
    }

    public static void validateRGComplete(int size) throws UnprocessableEntityException {
        if (size < 2)
            throw new UnprocessableEntityException(msg("identification.document.extraction.result.empty"));
    }

}
