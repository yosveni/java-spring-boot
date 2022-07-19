package com.linkapital.core.services.offer.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import lombok.Getter;

@Getter
public class OfferCommentWebsocketNotificationTO {

    private CommentTO comment;
    private String cnpj;
    private Long offerId;
    private Integer offerType;

    public OfferCommentWebsocketNotificationTO withComment(CommentTO comment) {
        this.comment = comment;
        return this;
    }

    public OfferCommentWebsocketNotificationTO withCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public OfferCommentWebsocketNotificationTO withOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public OfferCommentWebsocketNotificationTO withOfferType(Integer offerType) {
        this.offerType = offerType;
        return this;
    }

}
