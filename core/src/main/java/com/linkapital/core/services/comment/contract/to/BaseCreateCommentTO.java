package com.linkapital.core.services.comment.contract.to;

import com.linkapital.core.services.comment.contract.enums.CommentArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "the common data to create a comment.")
@Getter
@Setter
public class BaseCreateCommentTO {

    @ApiModelProperty(value = "The comment.", required = true)
    @NotNull
    private String comment;

    @ApiModelProperty(value = "The comment area (CLIENT, BACKOFFICE).", required = true)
    @NotNull
    private CommentArea commentArea;

}
