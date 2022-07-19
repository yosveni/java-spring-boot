package com.linkapital.core.services.comment;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentOfferTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentTO;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Default interface for {@link CommentService}
 *
 * @since 0.0.1
 */
public interface CommentService {

    /**
     * Save comments to data base.
     *
     * @param to {@link CreateCommentTO} the data needed to logout a comment
     * @return {@link Comment}
     * @throws UnprocessableEntityException if any error occurs
     */
    CommentTO create(CreateCommentTO to) throws UnprocessableEntityException;

    /**
     * Save comments to data base.
     *
     * @param to {@link CreateCommentOfferTO} the data needed to create a comment to offer
     * @return {@link Comment}
     * @throws UnprocessableEntityException if any error occurs
     */
    CommentTO createCommentOffer(CreateCommentOfferTO to) throws UnprocessableEntityException;

    /**
     * Upload file to a comment, set each file to the given comment, if the comment not found then throw an exception.
     *
     * @param id    {@link Long} the id of the comment
     * @param files {@link MultipartFile}[] the files to upload
     * @return {@link Comment}
     * @throws ResourceNotFoundException    if not found a comment by the id
     * @throws IOException                  if any error occur in delete directory process
     * @throws UnprocessableEntityException if any error occurs
     */
    CommentTO uploadFile(long id, MultipartFile[] files) throws ResourceNotFoundException, IOException, UnprocessableEntityException;

    /**
     * Update the view list of the comment with the authenticated user id by offer.
     *
     * @param offerId {@link Long} the offer id
     */
    void updateViews(long offerId);

    /**
     * Retrieve the comments of a offer from the datasource by id.
     *
     * @param id {@link Long} the offer id
     * @return {@link List}{@link CommentTO}
     */
    List<CommentTO> getAllByOfferId(long id);

}
