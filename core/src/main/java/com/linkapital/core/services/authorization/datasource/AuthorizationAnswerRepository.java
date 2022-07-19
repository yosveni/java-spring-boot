package com.linkapital.core.services.authorization.datasource;

import com.linkapital.core.services.authorization.datasource.domain.AuthorizationAnswer;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationAnswerRepository extends JpaRepository<AuthorizationAnswer, Long> {

    void deleteAllByQuestion(AuthorizationQuestion authorizationQuestion);

}
