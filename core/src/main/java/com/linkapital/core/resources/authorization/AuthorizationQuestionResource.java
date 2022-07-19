package com.linkapital.core.resources.authorization;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.AuthorizationQuestionService;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.question.CreateAuthorizationQuestionTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.services.authorization.contract.AuthorizationQuestionBinder.AUTHORIZATION_QUESTION_BINDER;

@Api(value = "Authorization Questions")
@RestController
@RequestMapping("/authorization_question")
public class AuthorizationQuestionResource {

    private final AuthorizationQuestionService authorizationQuestionService;

    public AuthorizationQuestionResource(AuthorizationQuestionService authorizationQuestionService) {
        this.authorizationQuestionService = authorizationQuestionService;
    }

    @ApiOperation(value = "Create authorization question.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthorizationQuestionTO> create(@ApiParam(value = "The authorization question to create.", required = true)
                                                          @RequestBody @Valid @NotNull CreateAuthorizationQuestionTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(authorizationQuestionService.create(to));
    }

    @ApiOperation(value = "Update authorization question.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthorizationQuestionTO> update(@ApiParam(value = "The authorization question data to update.", required = true)
                                                          @RequestBody @Valid @NotNull AuthorizationQuestionTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(authorizationQuestionService.update(to));
    }

    @ApiOperation(value = "Get authorization question by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AuthorizationQuestionTO> getById(@ApiParam(value = "The authorization question id.", required = true)
                                                           @PathVariable Long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(AUTHORIZATION_QUESTION_BINDER.bind(authorizationQuestionService.getById(id)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all authorization questions.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AuthorizationQuestionTO>> getAll() {
        return ResponseEntity.ok(authorizationQuestionService.getAll());
    }

    @ApiOperation(value = "Delete authorization question by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The authorization question id.", required = true)
                                       @PathVariable Long id) throws UnprocessableEntityException {
        authorizationQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

