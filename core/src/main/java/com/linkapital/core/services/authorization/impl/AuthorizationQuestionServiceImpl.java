package com.linkapital.core.services.authorization.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.AuthorizationAnswerService;
import com.linkapital.core.services.authorization.AuthorizationQuestionService;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.question.CreateAuthorizationQuestionTO;
import com.linkapital.core.services.authorization.datasource.AuthorizationQuestionRepository;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.authorization.contract.AuthorizationQuestionBinder.AUTHORIZATION_QUESTION_BINDER;

@Service
@Transactional
public class AuthorizationQuestionServiceImpl implements AuthorizationQuestionService {

    private final AuthorizationQuestionRepository authorizationQuestionRepository;
    private final AuthorizationAnswerService authorizationAnswerService;

    public AuthorizationQuestionServiceImpl(AuthorizationQuestionRepository authorizationQuestionRepository,
                                            AuthorizationAnswerService authorizationAnswerService) {
        this.authorizationQuestionRepository = authorizationQuestionRepository;
        this.authorizationAnswerService = authorizationAnswerService;
    }

    @Override
    public AuthorizationQuestion save(AuthorizationQuestion authorizationQuestion) {
        return authorizationQuestionRepository.save(authorizationQuestion);
    }

    @Override
    public AuthorizationQuestionTO create(CreateAuthorizationQuestionTO to) throws UnprocessableEntityException {
        var authorizationQuestion = AUTHORIZATION_QUESTION_BINDER.bind(to);
        return AUTHORIZATION_QUESTION_BINDER.bind(save(authorizationQuestion));
    }

    @Override
    public AuthorizationQuestionTO update(AuthorizationQuestionTO to) throws UnprocessableEntityException {
        var authorizationQuestion = AUTHORIZATION_QUESTION_BINDER.set(getById(to.getId()), to);
        return AUTHORIZATION_QUESTION_BINDER.bind(save(authorizationQuestion));
    }

    @Override
    public AuthorizationQuestion getById(Long id) throws UnprocessableEntityException {
        return authorizationQuestionRepository
                .findById(id)
                .orElseThrow(() ->
                        new UnprocessableEntityException(msg("owner.authorization.question.id.not.found", id)));
    }

    @Override
    public List<AuthorizationQuestionTO> getAll() {
        return AUTHORIZATION_QUESTION_BINDER.bindToListTO(authorizationQuestionRepository.findAll());
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(authorizationQuestion -> {
                    authorizationAnswerService.deleteAllByAuthorizationQuestion(authorizationQuestion);
                    authorizationQuestionRepository.delete(authorizationQuestion);
                });
    }

}
