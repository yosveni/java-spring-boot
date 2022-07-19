package com.linkapital.core.services.comment.contract.to;

import com.linkapital.core.services.comment.contract.enums.CommentArea;
import com.linkapital.core.services.comment.contract.enums.LearningSession;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.security.contract.to.LightUserTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All comment data.")
@Getter
@Setter
public class CommentTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The comment.")
    private String comment;

    @ApiModelProperty(value = "The learning number.")
    private int learningNumber;

    @ApiModelProperty(value = "The creation date.")
    private Date created;

    @ApiModelProperty(value = "The learningSession.")
    private LearningSession learningSession;

    @ApiModelProperty(value = "The comment area (CLIENT, BACKOFFICE).")
    private CommentArea commentArea;

    @ApiModelProperty(value = "The user data.")
    private LightUserTO user;

    @ApiModelProperty(value = "The list of id of the users who have already seen the message.")
    private List<Long> usersViews;

    @ApiModelProperty(value = "The list of attachments.")
    private List<DirectoryTO> attachments;

    public CommentTO() {
        this.usersViews = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

}
