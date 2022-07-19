package com.linkapital.core.resources.identification;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.IdentificationService;
import com.linkapital.core.services.identification.contract.enums.DocumentType;
import com.linkapital.core.util.multipart.Multipart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Api(value = "Identification Operations", description = "Operations pertaining to the identification")
@RestController
@RequestMapping("/identification")
@Validated
public class IdentificationResource {

    private static final String MAX_IMG_SIZE = "10MB";
    private static final String MAX_VIDEO_SIZE = "200MB";
    private final IdentificationService identificationService;

    public IdentificationResource(IdentificationService identificationService) {
        this.identificationService = identificationService;
    }

    @ApiOperation(value = "Context Extraction Process.")
    @PostMapping(value = "/context_extraction", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> contextExtraction(@ApiParam(value = "The file type.", required = true)
                                                  @RequestParam DocumentType type,
                                                  @ApiParam(value = "The front of the image to upload.", required = true)
                                                  @RequestPart @Multipart(maxSize = MAX_IMG_SIZE) MultipartFile frontFile,
                                                  @ApiParam(value = "The reverse of the image to upload.")
                                                  @RequestPart(required = false) @Multipart(maxSize = MAX_IMG_SIZE) MultipartFile reverseFile) throws UnprocessableEntityException {
        identificationService.contextExtraction(frontFile, reverseFile, type);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Liveness Detection Process.")
    @PostMapping(value = "/liveness_detection", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> livenessDetection(@ApiParam(value = "The file to upload.", required = true)
                                                  @RequestPart @Multipart(maxSize = MAX_VIDEO_SIZE) MultipartFile file) throws UnprocessableEntityException {
        identificationService.livenessDetection(file);
        return ResponseEntity.ok().build();
    }

}
