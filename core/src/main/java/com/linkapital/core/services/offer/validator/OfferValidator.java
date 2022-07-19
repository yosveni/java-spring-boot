package com.linkapital.core.services.offer.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.offer.contract.enums.OfferState;
import com.linkapital.core.services.offer.datasource.domain.Offer;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.ACCEPTED;
import static com.linkapital.core.services.company.contract.CompanyBinder.getIndicativeOfferByNumber;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.validateState;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.SELECTED;
import static com.linkapital.core.services.offer.contract.enums.OfferState.WITH_OFFER;

public class OfferValidator {

    private OfferValidator() {
    }

    //region Validate that the indicativeOffer is selected
    public static void validateIndicativeOffer(IndicativeOffer indicativeOffer) throws UnprocessableEntityException {
        if (!validateState.test(indicativeOffer, SELECTED))
            throw new UnprocessableEntityException(msg("company.fail.request.indicative.proposal"));
    }
    //endregion

    //region Validation to accept the offer
    public static void validateAcceptOffer(Offer offer, CompanyUser companyUser) throws UnprocessableEntityException {
        if (!offer.getState().equals(WITH_OFFER))
            throw new UnprocessableEntityException(msg("offer.not.bank.values.or.already.accepted"));

        var indicativeOffer = offer.getIndicativeOffer();
        var userIndicativeOffer = getIndicativeOfferByNumber.apply(companyUser, indicativeOffer.getType());

        if (userIndicativeOffer == null || !userIndicativeOffer.getId().equals(indicativeOffer.getId()))
            throw new UnprocessableEntityException(msg("offer.denied.for.user"));
    }
    //endregion

    //region Validate the owner authorization
    public static void validateOwnerAuthorization(CompanyUser companyUser) throws UnprocessableEntityException {
        var authorization = companyUser.getOwnerAuthorization();
        if (authorization == null || !authorization.getState().equals(ACCEPTED))
            throw new UnprocessableEntityException(msg("offer.user.not.authorized"));
    }
    //endregion

    //region Validate the offer state
    public static void validateState(OfferState currentState, OfferState state) throws UnprocessableEntityException {
        if (!currentState.equals(state))
            throw new UnprocessableEntityException(msg("offer.in.different.state", state));
    }
    //endregion

    //region Validate if the offer can be changed in status
    public static void validateChangeState(Offer offer) throws UnprocessableEntityException {
        if (!offer.isAccepted() && !"LINKAPITAL".equals(offer.getPartnerBank().getName()))
            throw new UnprocessableEntityException(msg("offer.in.different.state"));
    }
    //endregion

    //region Validate if the offer already has commission
    public static void validateHasCommission(Offer offer) throws UnprocessableEntityException {
        if (offer.getCommission() != null)
            throw new UnprocessableEntityException(msg("offer.with.commission"));
    }
    //endregion

}
