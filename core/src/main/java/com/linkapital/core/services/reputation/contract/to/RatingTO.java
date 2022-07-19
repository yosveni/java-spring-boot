package com.linkapital.core.services.reputation.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "All rating data.")
@Getter
@Setter
public class RatingTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The email of the user who makes the rating.")
    private String email;

    @ApiModelProperty(value = "The comment.")
    private String comment;

    @ApiModelProperty(value = "The rating value.")
    private int ratingValue;

}
