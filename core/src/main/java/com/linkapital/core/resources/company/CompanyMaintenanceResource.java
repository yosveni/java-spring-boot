package com.linkapital.core.resources.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyMaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Api(value = "Company maintenance Operations", description = "Operations pertaining to the companies")
@RestController
@RequestMapping("/company/maintenance")
public class CompanyMaintenanceResource {

    private final CompanyMaintenanceService companyMaintenanceService;

    public CompanyMaintenanceResource(CompanyMaintenanceService companyMaintenanceService) {
        this.companyMaintenanceService = companyMaintenanceService;
    }

    @ApiOperation(value = "Update data.")
    @PutMapping(value = "/{cnpj}")
    public ResponseEntity<Void> updateCompanyMaintenance(@ApiParam(value = "The company cnpj.", required = true)
                                                         @PathVariable String cnpj)
            throws UnprocessableEntityException, ResourceNotFoundException {
        companyMaintenanceService.updateForMaintenance(cnpj);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update data for companies.")
    @PutMapping()
    public ResponseEntity<Void> updateCompanyMaintenance() throws UnprocessableEntityException {
        companyMaintenanceService.updateAllMaintenance();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete company by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCompanyById(@ApiParam(value = "The company id.", required = true)
                                                  @PathVariable @Min(1) long id) {
        companyMaintenanceService.deleteCompanyById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete company user by id.")
    @DeleteMapping(value = "/company_user/{id}")
    public ResponseEntity<Void> deleteAllNotClient(@ApiParam(value = "The company user id.", required = true)
                                                   @PathVariable @Min(1) long id) {
        companyMaintenanceService.deleteCompanyUserId(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete company user by user id.")
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteCompanyUserById(@ApiParam(value = "The user id.", required = true)
                                                      @PathVariable @Min(1) long id) throws UnprocessableEntityException {
        companyMaintenanceService.deleteCompanyUser(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete all companies not client.")
    @DeleteMapping(value = "/not_client")
    public ResponseEntity<Void> deleteAllNotClient() {
        companyMaintenanceService.deleteAllNotClient();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete all companies and all companies users.")
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllCompanyUser() {
        companyMaintenanceService.deleteAllCompany();
        return ResponseEntity.ok().build();
    }

}
