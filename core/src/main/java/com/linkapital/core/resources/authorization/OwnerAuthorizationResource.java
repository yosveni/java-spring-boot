package com.linkapital.core.resources.authorization;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.OwnerAuthorizationService;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForAgentTO;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForOwnerTO;
import com.linkapital.core.services.authorization.contract.to.authorization.CancelAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.ClientAuthorizationDataTO;
import com.linkapital.core.services.authorization.contract.to.authorization.InitOwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.OwnerAuthorizationTO;
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

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Api(value = "Owner authorization.", description = "Operations pertaining to the owner authorization.")
@RestController
@RequestMapping("/authorization")
public class OwnerAuthorizationResource {

    private final OwnerAuthorizationService ownerAuthorizationService;

    public OwnerAuthorizationResource(OwnerAuthorizationService ownerAuthorizationService) {
        this.ownerAuthorizationService = ownerAuthorizationService;
    }

    @ApiOperation(value = "Init authorization for agent k.")
    @PostMapping(value = "/init/agent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> initForAgent(@ApiParam(value = "The data to init authorization.", required = true)
                                             @RequestBody @Valid @NotNull InitOwnerAuthorizationTO to) throws UnprocessableEntityException {
        ownerAuthorizationService.initForAgent(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Init authorization for owner.")
    @PostMapping(value = "/init/owner", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> initForOwner(@ApiParam(value = "The data to init authorization.", required = true)
                                             @RequestBody @Valid @NotNull AuthorizationAnswerForOwnerTO to) throws UnprocessableEntityException {
        ownerAuthorizationService.initForOwner(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update owner authorization data.")
    @PutMapping(value = "/owner/answer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OwnerAuthorizationTO> update(@ApiParam(value = "The data to answer the owner authorization.", required = true)
                                                       @RequestBody @Valid @NotNull AuthorizationAnswerForAgentTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(ownerAuthorizationService.answerQuestions(to));
    }

    @ApiOperation(value = "Get owner authorization by relation.")
    @GetMapping(value = "/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<OwnerAuthorizationTO> getByCnpj(@ApiParam(value = "The company cnpj.")
                                                          @PathVariable String cnpj,
                                                          @ApiParam(value = "The user id.")
                                                          @PathVariable long userId) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(ownerAuthorizationService.getByCompanyUser(cnpj, userId));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get owner authorization by token.")
    @GetMapping(value = "/owner/{token}", produces = "application/json")
    public ResponseEntity<ClientAuthorizationDataTO> getByToken(@ApiParam(value = "The owner authorization token.")
                                                                @PathVariable String token) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(ownerAuthorizationService.getForClient(token));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(msg("owner.authorization.token.not.found", token));
        }
    }

    @ApiOperation(value = "Cancel owner authorization.")
    @PutMapping(value = "/owner/cancel")
    public ResponseEntity<Void> cancel(@ApiParam(value = "The data to cancel the owner authorization.", required = true)
                                       @RequestBody @Valid @NotNull CancelAuthorizationTO to) throws UnprocessableEntityException {
        ownerAuthorizationService.cancel(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete owner authorization question by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The owner authorization id.", required = true)
                                       @PathVariable long id) throws UnprocessableEntityException {
        ownerAuthorizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
