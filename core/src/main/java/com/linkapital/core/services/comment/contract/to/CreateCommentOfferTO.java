package com.linkapital.core.services.comment.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data needed to comment offer.")
@Getter
@Setter
public class CreateCommentOfferTO extends BaseCreateCommentTO {

    @ApiModelProperty(value = "The offer id.", required = true)
    @NotNull
    @Min(1)
    private long offerId;

    @ApiModelProperty(value = "Indicates if the comment is to attach a file.")
    private boolean hasAttachment;

}
