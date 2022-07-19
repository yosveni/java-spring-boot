package com.linkapital.core.services.reputation.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to create the rating.")
@Getter
@Setter
public class CreateRatingTO {

    @ApiModelProperty(value = "The email of the user who makes the rating.", required = true)
    @NotEmpty
    @Email
    private String email;

    @ApiModelProperty(value = "The comment.", required = true)
    @NotEmpty
    private String comment;

    @ApiModelProperty(value = "The rating value.", required = true)
    @NotNull
    @Min(1)
    @Max(5)
    private int ratingValue;

}
