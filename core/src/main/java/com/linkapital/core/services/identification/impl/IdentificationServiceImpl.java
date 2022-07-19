package com.linkapital.core.services.identification.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.IdentificationService;
import com.linkapital.core.services.identification.contract.enums.DocumentType;
import com.linkapital.core.services.identification.contract.to.ContentExtractionDataTO;
import com.linkapital.core.services.identification.datasource.domain.Identification;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.identification.exceptions.EncoderIdentificationException;
import com.linkapital.identification.exceptions.FailedIdentificationException;
import com.linkapital.identification.exceptions.UnauthorizedIdentificationException;
import com.linkapital.identification.services.IIdentificationService;
import com.linkapital.identification.services.domains.content.ContentExtractionInfo;
import com.linkapital.identification.services.domains.content.ScoreData;
import com.linkapital.identification.services.domains.face.FaceCompareInfo;
import com.linkapital.identification.services.domains.liveness.LivenessDetectionInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.identification.contract.enums.DocumentRegion.BACK;
import static com.linkapital.core.services.identification.contract.enums.DocumentRegion.FRONT;
import static com.linkapital.core.services.identification.contract.enums.DocumentType.CNH;
import static com.linkapital.core.services.identification.contract.enums.DocumentType.RG;
import static com.linkapital.core.services.identification.contract.enums.IdentificationState.LIVENESS;
import static com.linkapital.core.services.identification.contract.enums.IdentificationState.OCR;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.getValidDate;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validate;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validateContentExtractionInfo;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validateContextExtraction;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validateFaceCompare;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validateLivenessDetection;
import static com.linkapital.core.services.identification.validator.IdentificationValidator.validateObjectIsNull;

@Service
@Transactional
public class IdentificationServiceImpl implements IdentificationService {

    public static final String FORMAT = "yyyyMMdd";
    private final IIdentificationService identificationService;
    private final UserService userService;

    public IdentificationServiceImpl(IIdentificationService identificationService,
                                     UserService userService) {
        this.identificationService = identificationService;
        this.userService = userService;
    }

    @Override
    public void contextExtraction(MultipartFile frontFile,
                                  MultipartFile reverseFile,
                                  DocumentType type) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validate(user.getIdentification());

        var data = processContextExtraction(frontFile, type);
        var front = data.getFront();
        var reverse = data.getBack();
        byte[] reverseImage = null;
        validateContextExtraction(front, type, FRONT);

        if (reverseFile != null) {
            reverse = processContextExtraction(reverseFile, type).getBack();
            reverseImage = reverse.getImage();
        }

        if ((CNH.equals(type) && reverse != null) || RG.equals(type))
            validateContextExtraction(reverse, type, BACK);

        updateRelationUserIdentification(
                type.name(),
                CNH.equals(type)
                        ? getValidDate(front.getFields())
                        : getExpeditionDate(reverse.getFields()),
                user,
                front.getImage(),
                reverseImage);
    }

    @Override
    public void livenessDetection(MultipartFile file) throws UnprocessableEntityException {
        var userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        var user = userService.getById(userId);
        var identification = user.getIdentification();

        validateObjectIsNull(identification, msg("identification.document.not.found"));

        var response = getLivenessDetectionInfo(file);
        var image = response.getResult().getFrontalImage();

        user = updateUserInLivenessDetection(user, image);
        faceCompare(identification.getDocument(), image);
        userService.updateUserIdentificationByIntensity(user);
    }

    //region Face Compare
    private void faceCompare(byte[] base64Document, byte[] base64Image) throws UnprocessableEntityException {
        FaceCompareInfo response;
        try {
            response = identificationService.faceCompare(base64Document, base64Image);
        } catch (FailedIdentificationException e) {
            throw new UnprocessableEntityException(e.getMessage());
        } catch (UnauthorizedIdentificationException e) {
            throw new UnprocessableEntityException(msg("identification.authentication.error"));
        }

        validateFaceCompare(response.getResult());
    }
    //endregion

    //region Obtain info of content extraction
    private ContentExtractionInfo getContentExtractionInfo(MultipartFile file,
                                                           DocumentType type) throws UnprocessableEntityException {
        ContentExtractionInfo response;
        try {
            response = identificationService.contextExtraction(file, type.toString());
        } catch (FailedIdentificationException e) {
            throw new UnprocessableEntityException(e.getMessage());
        } catch (UnauthorizedIdentificationException e) {
            throw new UnprocessableEntityException(msg("identification.authentication.error"));
        }

        validateContentExtractionInfo(response);

        return response;
    }
    //endregion

    //region Obtain Liveness Detection Info
    private LivenessDetectionInfo getLivenessDetectionInfo(MultipartFile file) throws UnprocessableEntityException {
        LivenessDetectionInfo response;
        try {
            response = identificationService.livenessDetection(file);
        } catch (FailedIdentificationException e) {
            throw new UnprocessableEntityException(e.getMessage());
        } catch (UnauthorizedIdentificationException e) {
            throw new UnprocessableEntityException(msg("identification.authentication.error"));
        } catch (EncoderIdentificationException e) {
            throw new UnprocessableEntityException(msg("identification.encoder.error"));
        }

        validateLivenessDetection(response);

        return response;
    }
    //endregion

    //region Obtain Context Extraction from file
    private ContentExtractionDataTO processContextExtraction(MultipartFile file,
                                                             DocumentType type) throws UnprocessableEntityException {
        var response = getContentExtractionInfo(file, type);
        var result = response.getResult();
        var data = new ContentExtractionDataTO();

        result.forEach(contentExtraction -> {
            if (contentExtraction.getTags()
                    .stream()
                    .anyMatch("region=front"::equals))
                data.setFront(contentExtraction);
            else
                data.setBack(contentExtraction);
        });

        return data;
    }
    //endregion

    //region Update Relation User Identification
    private void updateRelationUserIdentification(String type,
                                                  Date valid,
                                                  User user,
                                                  byte[] image,
                                                  byte[] reverseImage) {
        userService.save(user
                .withIdentification(new Identification()
                        .withType(type)
                        .withValid(valid)
                        .withState(OCR)
                        .withDocument(image)
                        .withReverseDocument(reverseImage)));
    }
    //endregion

    //region Update user data after finishing the Liveness Detection process
    private User updateUserInLivenessDetection(User user, byte[] selfieCapture) {
        var identification = user.getIdentification();
        identification.setState(LIVENESS);
        identification.setSelfieCapture(selfieCapture);

        return userService.save(user);
    }
    //endregion

    //region Obtains the issuance date of the RG document
    private Date getExpeditionDate(List<ScoreData> fields) {
        return fields
                .stream()
                .filter(scoreData -> "data_expedicao".equals(scoreData.getName()))
                .findFirst()
                .map(scoreData -> {
                    try {
                        return new SimpleDateFormat(FORMAT)
                                .parse(scoreData.getValue().replace("-", ""));
                    } catch (ParseException e) {
                        return new Date();
                    }
                })
                .orElse(new Date());
    }
    //endregion

}
