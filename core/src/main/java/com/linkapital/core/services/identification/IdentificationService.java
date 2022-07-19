package com.linkapital.core.services.identification;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.contract.enums.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public interface IdentificationService {

    void contextExtraction(@NotNull MultipartFile frontFile, MultipartFile reverseFile, @NotNull DocumentType type) throws UnprocessableEntityException;

    void livenessDetection(@NotNull MultipartFile file) throws UnprocessableEntityException;

}
