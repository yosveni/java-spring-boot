package com.linkapital.core.services.identification.contract.to;

import com.linkapital.core.services.identification.contract.enums.IdentificationState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The identification data.")
@Getter
@Setter
public class IdentificationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The document type.")
    private String type;

    @ApiModelProperty(value = "The valid date of the document.")
    private Date valid;

    @ApiModelProperty(value = "The created date.")
    private Date created;

    @ApiModelProperty(value = "The identification state.")
    private IdentificationState state;

    @ApiModelProperty(value = "The image of the document sent.")
    private byte[] document;

    @ApiModelProperty(value = "The image of the document on the back sent.")
    private byte[] reverseDocument;

    @ApiModelProperty(value = "The selfie image captured in the sent video.")
    private byte[] selfieCapture;

}