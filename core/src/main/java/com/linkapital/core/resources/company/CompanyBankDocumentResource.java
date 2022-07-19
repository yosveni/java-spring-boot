package com.linkapital.core.resources.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankDocumentTO;
import com.linkapital.core.services.company_user.CompanyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Company Bank Document Operations", description = "Operations pertaining to the company bank documents")
@RestController
@RequestMapping("/company")
public class CompanyBankDocumentResource {

    private final CompanyUserService companyUserService;

    public CompanyBankDocumentResource(CompanyUserService companyUserService) {
        this.companyUserService = companyUserService;
    }

    @ApiOperation(value = "Update bank document state.")
    @PutMapping(value = "/bank_documents/change_state", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyBankDocumentTO> updateBankDocumentState(@ApiParam(value = "The bank document data.", required = true)
                                                                         @RequestBody @NotNull @Valid UpdateBankDocumentTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.updateBankDocumentState(to));
    }

    @ApiOperation(value = "Get all bank documents of company")
    @GetMapping(value = "/bank_documents/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<List<CompanyBankDocumentTO>> getBankDocuments(@ApiParam(value = "The company cnpj.", required = true)
                                                                        @PathVariable String cnpj,
                                                                        @ApiParam(value = "The user id.", required = true)
                                                                        @PathVariable @Min(1) long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyUserService.getBankDocuments(cnpj, userId));
    }

}
