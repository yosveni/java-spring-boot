package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;


@ApiModel(description = "The user temp data.")
@Getter
@Setter
public class UserTempTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The code country phone.")
    private String codeCountryPhone;

    @ApiModelProperty(value = "The phone number.")
    private String phone;

    @ApiModelProperty(value = "The linkeding contact url.")
    private String linkedingContact;

    @ApiModelProperty(value = "The user cpf.")
    private String cpf;

    @ApiModelProperty(value = "Indicated if the user is partner.")
    private boolean partner;

    @ApiModelProperty(value = "The created date.")
    private Date created;

    @ApiModelProperty(value = "The modified date.")
    private Date modified;

}
