package com.linkapital.core.resources.comment;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.comment.CommentService;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentOfferTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Api(value = "Comments Operations.", description = "Operations pertaining to the comments.")
@RestController
@RequestMapping("/comment")
public class CommentResource {

    private final CommentService commentService;

    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create comment.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentTO> create(@ApiParam(value = "The data needed to create a comment.", required = true)
                                            @RequestBody @NotNull @Valid CreateCommentTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(commentService.create(to));
    }

    @ApiOperation(value = "Create comment for offer.")
    @PostMapping(value = "/offer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentTO> createCommentOffer(@ApiParam(value = "The data needed to create a comment offer.", required = true)
                                                        @RequestBody @NotNull @Valid CreateCommentOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(commentService.createCommentOffer(to));
    }

    @ApiOperation(value = "Upload file.")
    @PostMapping(value = "/upload/{id}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CommentTO> upload(@ApiParam(value = "The comment id.", required = true)
                                            @PathVariable long id,
                                            @ApiParam(value = "The files to upload.", required = true)
                                            @RequestPart MultipartFile[] files) throws IOException, UnprocessableEntityException, ResourceNotFoundException {
        return ResponseEntity.ok(commentService.uploadFile(id, files));
    }

    @ApiOperation(value = "Update the view list of the comment with the authenticated user id.")
    @PutMapping(value = "/views/{offerId}")
    public ResponseEntity<Void> updateViews(@ApiParam(value = "The offer id.", required = true)
                                            @PathVariable long offerId) {
        commentService.updateViews(offerId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get comments from offer by id.")
    @GetMapping(value = "/offer/{id}", produces = "application/json")
    public ResponseEntity<List<CommentTO>> getAllByOfferId(@ApiParam(value = "The offer id.", required = true)
                                                           @PathVariable long id) {
        return ResponseEntity.ok(commentService.getAllByOfferId(id));
    }

}
