package com.linkapital.core.services.comment.impl;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.comment.CommentService;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentOfferTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentTO;
import com.linkapital.core.services.comment.datasource.CommentRepository;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.contract.to.OfferCommentWebsocketNotificationTO;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.security.contract.enums.Authority;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.comment.contract.CommentBinder.COMMENT_BINDER;
import static com.linkapital.core.services.comment.contract.CommentBinder.buildBasicCommentOffer;
import static com.linkapital.core.services.notification.WebsocketService.COMMENT_CREATED;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static com.linkapital.core.services.security.contract.enums.Authority.BACKOFFICE;
import static com.linkapital.core.services.security.contract.enums.Authority.SECURITY;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_BACK;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_SEC;
import static java.util.Arrays.asList;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CompanyUserService companyUserService;
    private final DirectoryService directoryService;
    private final OfferService offerService;
    private final WebsocketService webSocketService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CompanyUserService companyUserService,
                              DirectoryService directoryService,
                              OfferService offerService,
                              WebsocketService webSocketService) {
        this.commentRepository = commentRepository;
        this.companyUserService = companyUserService;
        this.directoryService = directoryService;
        this.offerService = offerService;
        this.webSocketService = webSocketService;
    }

    @Override
    public CommentTO create(@NonNull CreateCommentTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authority = user.getRole().getAuthority();
        var comment = COMMENT_BINDER.bind(to)
                .withUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .withCompanyUser(companyUser);
        comment = save(comment);
        var commentTO = COMMENT_BINDER.bind(comment);

        if (!to.isHasAttachment())
            emitNotification(to.getCnpj(),
                    companyUser.getUser().getEmail(),
                    authority,
                    null,
                    null,
                    commentTO);

        return commentTO;
    }

    @Override
    public CommentTO createCommentOffer(@NonNull CreateCommentOfferTO to) throws UnprocessableEntityException {
        var offer = offerService.getById(to.getOfferId());
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authority = user.getRole().getAuthority();

        offer = offerService.save(offer
                .withComments(buildBasicCommentOffer.apply(to, user, offer.getType())));

        var comments = offer.getComments();
        var comment = comments
                .stream()
                .toList()
                .get(comments.size() - 1);
        var commentTO = COMMENT_BINDER.bind(comment);

        if (!to.isHasAttachment())
            emitNotification(offer.getCnpj(),
                    offer.getUser().getEmail(),
                    authority,
                    offer.getId(),
                    offer.getType(),
                    commentTO);

        return commentTO;
    }

    @Override
    public CommentTO uploadFile(long id,
                                MultipartFile @NonNull [] files) throws UnprocessableEntityException {
        if (files.length == 0)
            throw new UnprocessableEntityException(msg("comment.file.not.uploaded"));

        Comment comment;
        try {
            comment = getById(id);
        } catch (ResourceNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        directoryService.deleteFiles(comment.getAttachments());
        comment.getAttachments().clear();
        directoryService.uploadCommentFiles(files, comment);

        var to = COMMENT_BINDER.bind(commentRepository.save(comment));
        emitNotificationForUploadFile(comment.getOffer(), comment.getCompanyUser(), to);

        return to;
    }

    @Override
    public void updateViews(long offerId) {
        var userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        var comments = commentRepository.filter(userId, offerId);

        if (!comments.isEmpty())
            commentRepository.saveAll(comments
                    .stream()
                    .map(comment -> comment.withUsersViews(userId))
                    .toList());
    }

    @Override
    public List<CommentTO> getAllByOfferId(long id) {
        return COMMENT_BINDER.bind(commentRepository.findAllByOffer_Id(id));
    }

    private @NonNull Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    //region Get a Comment given an id
    private Comment getById(Long id) throws ResourceNotFoundException {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(msg("comment.id.not.found", id)));
    }
    //endregion

    //region Issues the socket notification for upload file
    private void emitNotificationForUploadFile(Offer offer, CompanyUser companyUser, CommentTO to) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authority = user.getRole().getAuthority();

        if (offer == null && companyUser != null)
            emitNotification(companyUser.getCompany().getMainInfo().getCnpj(),
                    companyUser.getUser().getEmail(),
                    authority,
                    null,
                    null,
                    to);
        else if (offer != null && companyUser == null)
            emitNotification(offer.getCnpj(),
                    offer.getUser().getEmail(),
                    authority,
                    offer.getId(),
                    offer.getType(),
                    to);
    }
    //endregion

    //region Issues the socket notification with the necessary data
    private void emitNotification(String cnpj,
                                  String recipientEmail,
                                  @NonNull Authority authority,
                                  Long offerId,
                                  Integer offerType,
                                  CommentTO comment) {

        var data = new OfferCommentWebsocketNotificationTO()
                .withCnpj(cnpj)
                .withOfferId(offerId)
                .withOfferType(offerType)
                .withComment(comment);

        if (authority.equals(BACKOFFICE) || authority.equals(SECURITY))
            webSocketService.dispatch(
                    recipientEmail,
                    TOPIC,
                    COMMENT_CREATED,
                    msg("offer.comment.created.for.client"),
                    data);
        else
            webSocketService.dispatch(
                    asList(LKP_BACK, LKP_SEC),
                    TOPIC,
                    COMMENT_CREATED,
                    msg("offer.comment.created"),
                    data);
    }
    //endregion


}
