package com.linkapital.core.services.authorization.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import com.linkapital.core.services.authorization.contract.to.answer.CreateAuthorizationAnswerTO;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.PROGRESS;

public class OwnerAuthorizationValidator {

    private OwnerAuthorizationValidator() {
    }

    //region Validate that existing questions have answers
    public static void validateQuestionAndAnswers(List<AuthorizationQuestionTO> questions,
                                                  List<CreateAuthorizationAnswerTO> answers) throws UnprocessableEntityException {
        if (!questions.isEmpty() && answers.isEmpty())
            throw new UnprocessableEntityException(msg("owner.authorization.questions.pending"));
    }
    //endregion

    //region Validate that the element is not null
    public static void validateState(AuthorizationState state) throws UnprocessableEntityException {
        if (state.equals(PROGRESS))
            return;

        throw new UnprocessableEntityException(msg("owner.authorization.already"));
    }
    //endregion

}
