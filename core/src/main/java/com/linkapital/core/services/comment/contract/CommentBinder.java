package com.linkapital.core.services.comment.contract;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentOfferTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentTO;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

import static com.linkapital.core.services.comment.contract.enums.LearningSession.OFFER;

/**
 * Default binding class for {@link Comment} transformations
 * Class in charge of constructing the objects received in the comments handler requests and converting them into
 * domain classes
 *
 * @since 0.0.1
 */
@Mapper
public interface CommentBinder {

    CommentBinder COMMENT_BINDER = Mappers.getMapper(CommentBinder.class);

    TriFunction<CreateCommentOfferTO, User, Integer, Comment> buildBasicCommentOffer = (to, user, type) ->
            new Comment()
                    .withComment(to.getComment())
                    .withCommentArea(to.getCommentArea())
                    .withLearningSession(OFFER)
                    .withLearningNumber(type)
                    .withUser(user)
                    .withUsersViews(user.getId());

    CommentTO bind(Comment source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "companyUser", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    Comment bind(CreateCommentTO source);

    default List<CommentTO> bind(List<Comment> source) {
        return source
                .stream()
                .map(this::bind)
                .toList();
    }

    default List<CommentTO> filterComments(Set<Comment> source, Integer learningNumber) {
        var comments = source
                .stream()
                .filter(comment -> comment.getLearningNumber() == learningNumber)
                .toList();

        return bind(comments);
    }

}
