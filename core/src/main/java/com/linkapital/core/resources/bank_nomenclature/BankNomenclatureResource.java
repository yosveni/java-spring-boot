package com.linkapital.core.resources.bank_nomenclature;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.BankNomenclatureService;
import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankNomenclatureTO;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.offer.impl.FacadeOfferServiceImpl;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.BANK_NOMENCLATURE_BINDER;

@Api(value = "Bank Nomenclature Operations.", description = "Operations pertaining to the bank nomenclature.")
@RestController
@RequestMapping("/bank_nomenclature")
public class BankNomenclatureResource {

    private final BankNomenclatureService bankNomenclatureService;
    private final CompanyUserService companyUserService;
    private final FacadeOfferServiceImpl offerService;

    public BankNomenclatureResource(BankNomenclatureService bankNomenclatureService,
                                    CompanyUserService companyUserService,
                                    FacadeOfferServiceImpl offerService) {
        this.bankNomenclatureService = bankNomenclatureService;
        this.companyUserService = companyUserService;
        this.offerService = offerService;
    }

    @ApiOperation(value = "Create bank nomenclature.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BankNomenclatureTO> create(@ApiParam(value = "The bank nomenclature data.", required = true)
                                                     @RequestBody @NotNull @Valid CreateBankNomenclatureTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(bankNomenclatureService.create(to));
    }

    @ApiOperation(value = "Update bank nomenclature.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BankNomenclatureTO> update(@ApiParam(value = "The bank nomenclature data.", required = true)
                                                     @RequestBody @NotNull @Valid UpdateBankNomenclatureTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(bankNomenclatureService.update(to));
    }

    @ApiOperation(value = "Get all the nomenclators belonging to the selected products in the accepted offer of the" +
            " company.")
    @GetMapping(value = "/all/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<List<CompanyBankNomenclatureTO>> getBankNomenclatures(@ApiParam(value = "The company cnpj.", required = true)
                                                                                @PathVariable String cnpj,
                                                                                @ApiParam(value = "The user id.", required = true)
                                                                                @PathVariable @Min(1) long userId) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(bankNomenclatureService.getAllByCompanyUser(companyUserService.get(cnpj, userId)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all the nomenclators by offer id")
    @GetMapping(value = "/offer/{offerId}", produces = "application/json")
    public ResponseEntity<List<CompanyBankNomenclatureTO>> getBankNomenclatures(@ApiParam(value = "The offer id.", required = true)
                                                                                @PathVariable @Min(1) long offerId) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(offerService.getAllNomenclaturesById(offerId));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get bank nomenclature by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BankNomenclatureTO> getById(@ApiParam(value = "The bank nomenclature id.", required = true)
                                                      @PathVariable @Min(1) long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(BANK_NOMENCLATURE_BINDER.bind(bankNomenclatureService.getById(id)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all bank nomenclatures.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BankNomenclatureTO>> getAll() {
        return ResponseEntity.ok(bankNomenclatureService.getAll());
    }

    @ApiOperation(value = "Delete bank nomenclature by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The bank nomenclature id.", required = true)
                                       @PathVariable @Min(1) long id) throws UnprocessableEntityException {
        try {
            bankNomenclatureService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException(msg("bank.nomenclature.not.deleted"));
        }

        return ResponseEntity.ok().build();
    }

}
