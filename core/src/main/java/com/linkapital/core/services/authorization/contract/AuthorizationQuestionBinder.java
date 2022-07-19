package com.linkapital.core.services.authorization.contract;

import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.question.CreateAuthorizationQuestionTO;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorizationQuestionBinder {

    AuthorizationQuestionBinder AUTHORIZATION_QUESTION_BINDER = Mappers.getMapper(AuthorizationQuestionBinder.class);

    AuthorizationQuestionTO bind(AuthorizationQuestion source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    AuthorizationQuestion bind(CreateAuthorizationQuestionTO source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    AuthorizationQuestion bind(AuthorizationQuestionTO source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    AuthorizationQuestion set(@MappingTarget AuthorizationQuestion target, AuthorizationQuestionTO source);

    List<AuthorizationQuestionTO> bindToListTO(List<AuthorizationQuestion> source);

}
