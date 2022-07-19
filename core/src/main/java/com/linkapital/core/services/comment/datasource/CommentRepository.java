package com.linkapital.core.services.comment.datasource;

import com.linkapital.core.services.comment.datasource.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Default interface repository for {@link CommentRepository}
 * Make database operations for {@link Comment} entity
 *
 * @since 0.0.1
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select *\n" +
            "from tab_comment tc\n" +
            "         join tab_offer t on t.id = tc.offer_id\n" +
            "         left join tab_comment_users_views tcuv on tc.id = tcuv.tab_comment_id\n" +
            "where tc.offer_id = ?2 and tcuv.users_views is distinct from ?1", nativeQuery = true)
    List<Comment> filter(long userId, long offerId);

    List<Comment> findAllByOffer_Id(long offerId);

}
