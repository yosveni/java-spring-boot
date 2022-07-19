package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.company.contract.enums.CreditApplicationFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The user guide.")
@Getter
@Setter
public class UserGuideTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "If add company's audio has been heard.")
    private boolean addCompanyAudio;

    @ApiModelProperty(value = "If aval's audio has been heard.")
    private boolean avalAudio;

    @ApiModelProperty(value = "If receivables discount's audio has been heard.")
    private boolean discountAudio;

    @ApiModelProperty(value = "If recurring revenue's audio has been heard.")
    private boolean reAudio;

    @ApiModelProperty(value = "If properties 's audio has been heard.")
    private boolean imAudio;

    @ApiModelProperty(value = "The general company")
    private boolean generalCompany;

    @ApiModelProperty(value = "If the complete guide has been heard and seen.")
    private boolean completed;

    @ApiModelProperty(value = "Until which part of the credit application process the client has advanced")
    private CreditApplicationFlow creditApplicationFlow;

}
