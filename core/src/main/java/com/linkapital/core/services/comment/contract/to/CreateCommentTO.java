package com.linkapital.core.services.comment.contract.to;

import com.linkapital.core.services.comment.contract.enums.LearningSession;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data needed to logout a comment.")
@Getter
@Setter
public class CreateCommentTO extends BaseCreateCommentTO {

    @ApiModelProperty(value = "The cnpj of the company.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The user id.")
    @Min(1)
    private Long userId;

    @ApiModelProperty(value = "The learningSession.", required = true)
    @NotNull
    private LearningSession learningSession;

    @ApiModelProperty(value = "The learning number.", required = true)
    @NotNull
    @Min(0)
    @Max(4)
    private int learningNumber;

    @ApiModelProperty(value = "Indicates if the comment is to attach a file.")
    private boolean hasAttachment;

}
