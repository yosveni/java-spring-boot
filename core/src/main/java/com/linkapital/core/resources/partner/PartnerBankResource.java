package com.linkapital.core.resources.partner;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.partner_bank.PartnerBankService;
import com.linkapital.core.services.partner_bank.contract.to.create.CreatePartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.update.UpdatePartnerBankTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.dao.DataIntegrityViolationException;
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

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.partner_bank.contract.PartnerBankBinder.PARTNER_BANK_BINDER;

@Api(value = "Partner Bank Operations.", description = "Operations pertaining to the partner bank.")
@RestController
@RequestMapping("/partner_bank")
public class PartnerBankResource {

    private final PartnerBankService partnerBankService;

    public PartnerBankResource(PartnerBankService partnerBankService) {
        this.partnerBankService = partnerBankService;
    }

    @ApiOperation(value = "Create partner bank.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PartnerBankTO> save(@ApiParam(value = "The partner bank data.", required = true)
                                              @RequestBody @NotNull @Valid CreatePartnerBankTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(partnerBankService.create(to));
    }

    @ApiOperation(value = "Update partner bank.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PartnerBankTO> update(@ApiParam(value = "The partner bank data.", required = true)
                                                @RequestBody @NotNull @Valid UpdatePartnerBankTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(partnerBankService.update(to));
    }

    @ApiOperation(value = "Get partner bank by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PartnerBankTO> getById(@ApiParam(value = "The partner bank id.", required = true)
                                                 @PathVariable Long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(PARTNER_BANK_BINDER.bind(partnerBankService.getById(id)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all partner bank.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PartnerBankTO>> getAll() {
        return ResponseEntity.ok(partnerBankService.getAll());
    }

    @ApiOperation(value = "Delete partner bank by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The partner bank id.", required = true)
                                       @PathVariable Long id) throws UnprocessableEntityException {
        try {
            partnerBankService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException(msg("partner.bank.not.deleted"));
        }

        return ResponseEntity.ok().build();
    }

}
