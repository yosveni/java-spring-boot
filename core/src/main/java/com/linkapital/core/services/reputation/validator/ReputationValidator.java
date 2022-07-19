package com.linkapital.core.services.reputation.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

public class ReputationValidator {

    private ReputationValidator() {
    }

    public static void validateRated(boolean rated) throws UnprocessableEntityException {
        if (rated)
            throw new UnprocessableEntityException(msg("reputation.user.has.already.evaluated.system"));
    }

}
