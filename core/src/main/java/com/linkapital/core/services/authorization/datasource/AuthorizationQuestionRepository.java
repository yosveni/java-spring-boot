package com.linkapital.core.services.authorization.datasource;

import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationQuestionRepository extends JpaRepository<AuthorizationQuestion, Long> {

}
