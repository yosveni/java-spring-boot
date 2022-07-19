package com.linkapital.core.services.authorization.impl;

import com.linkapital.core.services.authorization.AuthorizationAnswerService;
import com.linkapital.core.services.authorization.datasource.AuthorizationAnswerRepository;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizationAnswerServiceImpl implements AuthorizationAnswerService {

    private final AuthorizationAnswerRepository authorizationAnswerRepository;

    public AuthorizationAnswerServiceImpl(AuthorizationAnswerRepository authorizationAnswerRepository) {
        this.authorizationAnswerRepository = authorizationAnswerRepository;
    }

    @Override
    public void deleteAllByAuthorizationQuestion(AuthorizationQuestion authorizationQuestion) {
        authorizationAnswerRepository.deleteAllByQuestion(authorizationQuestion);
    }

}
