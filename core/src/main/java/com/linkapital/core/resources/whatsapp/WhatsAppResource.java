package com.linkapital.core.resources.whatsapp;

import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.whatsapp.WhatsAppService;
import com.linkapital.core.services.whatsapp.contract.to.CreateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.DefaultMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UserWhatsAppTO;
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

import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.WHATS_APP_BINDER;

@Api(value = "WhatsApp Operations", tags = "whatsapp-resource", description = "Operations pertaining to the whatsApp")
@RestController
@RequestMapping("/whatsapp")
public class WhatsAppResource {

    private final WhatsAppService whatsAppService;

    public WhatsAppResource(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @ApiOperation(value = "Create user whatsapp.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserWhatsAppTO> saveUserWhatsApp(@ApiParam(value = "WhatsApp user data to create.", required = true)
                                                           @RequestBody @NotNull @Valid CreateUserWhatsAppTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(whatsAppService.save(to));
    }

    @ApiOperation(value = "Update user whatsapp.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserWhatsAppTO> updateUserWhatsApp(@ApiParam(value = "WhatsApp user data to update.", required = true)
                                                             @RequestBody @Valid @NotNull UpdateUserWhatsAppTO to) throws ResourceNotFoundException, UnprocessableEntityException {
        return ResponseEntity.ok(whatsAppService.update(to));
    }

    @ApiOperation(value = "Get user whatsapp by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserWhatsAppTO> getCompanyLearningDetails(@ApiParam(value = "The user whatsApp id.", required = true)
                                                                    @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(WHATS_APP_BINDER.bind(whatsAppService.getById(id)));
    }

    @ApiOperation(value = "Get all user whatsapp.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserWhatsAppTO>> getAll() {
        return ResponseEntity.ok(whatsAppService.getAll());
    }

    @ApiOperation(value = "Get random user whatsapp.")
    @GetMapping(value = "/random", produces = "application/json")
    public ResponseEntity<UserWhatsAppTO> getRandomUser() throws UnprocessableEntityException {
        return ResponseEntity.ok(whatsAppService.getRandomUser());
    }

    @ApiOperation(value = "Delete all user whatsapp by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The user whatsApp id.", required = true)
                                       @PathVariable Long id) throws ResourceNotFoundException {
        whatsAppService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Load default message of whatsapp.")
    @GetMapping(value = "/default_message", produces = "application/json")
    public ResponseEntity<DefaultMessageWhatsAppTO> getDefaultMessage() throws NotFoundSystemConfigurationException {
        return ResponseEntity.ok(whatsAppService.getDefaultMessage());
    }

    @ApiOperation(value = "Update default message of whatsapp.")
    @PutMapping(value = "/default_message", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DefaultMessageWhatsAppTO> setDefaultMessage(@ApiParam(value = "The message whatsapp to update.", required = true)
                                                                      @RequestBody @Valid @NotNull UpdateMessageWhatsAppTO to) throws NotFoundSystemConfigurationException {
        return ResponseEntity.ok(whatsAppService.updateDefaultMessage(to));
    }

}
