package com.linkapital.core.resources.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.contract.to.CompanyLocationTO;
import com.linkapital.core.services.company.contract.to.CompanyTO;
import com.linkapital.core.services.company.contract.to.CreateCnpjListTO;
import com.linkapital.core.services.company.contract.to.LearningSessionConfirmTO;
import com.linkapital.core.services.company.contract.to.SessionConfirmedTO;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyTransferTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.method_k.contract.to.ScoreSummaryTO;
import com.linkapital.core.services.offer.contract.enums.FilterOfferType;
import com.linkapital.core.services.person.contract.to.PersonPartnerTO;
import com.linkapital.core.util.generic.GenericFilterTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.services.person.contract.PersonBinder.PERSON_BINDER;

@Api(value = "Company Operations", description = "Operations pertaining to the companies")
@RestController
@RequestMapping("/company")
@Validated
public class CompanyResource {

    private final CompanyService companyService;
    private final CompanyUserService companyUserService;

    public CompanyResource(CompanyService companyService,
                           CompanyUserService companyUserService) {
        this.companyService = companyService;
        this.companyUserService = companyUserService;
    }

    @ApiOperation(value = "Create company backoffice.")
    @PostMapping(value = "/list/cnpj", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<LightBackOfficeTO>> saveCnpjList(@ApiParam(value = "The list of cnpj.", required = true)
                                                                @RequestBody @NotNull @Valid CreateCnpjListTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.saveCnpjList(to.getCnpjList()));
    }

    @ApiOperation(value = "Confirm learning session.")
    @PostMapping(value = "/session/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SessionConfirmedTO> confirmLearningSession(@ApiParam(value = "The data to confirm learning session.", required = true)
                                                                     @RequestBody @Valid @NotNull LearningSessionConfirmTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.confirmLearningSession(to));
    }

    @ApiOperation(value = "Reset company owner.")
    @PutMapping(value = "/reset_owner/{cnpj}", produces = "application/json")
    public ResponseEntity<Void> resetOwner(@ApiParam(value = "The company cnpj.", required = true)
                                           @PathVariable String cnpj) throws ResourceNotFoundException {
        companyService.resetOwner(cnpj);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Transfer company to other user.")
    @PutMapping(value = "/transfer", produces = "application/json")
    public ResponseEntity<LightBackOfficeTO> transferCompanyToUser(@ApiParam(value = "The data to transfer a company to another user.", required = true)
                                                                   @RequestBody @NotNull @Valid CompanyTransferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.transferCompanyToUser(to));
    }

    @ApiOperation(value = "Update credits information of company by cnpj.")
    @PutMapping(value = "/credit_information/{cnpj}", produces = "application/json")
    public ResponseEntity<List<CreditInformationTO>> updateCreditsInformationByCnpj(@ApiParam(value = "The company cnpj.", required = true)
                                                                                    @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyService.updateCreditsInformationByCnpj(cnpj));
    }

    @ApiOperation(value = "Get all companies.")
    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseEntity<GenericFilterTO<LightBackOfficeTO>> getByFilterGlobal(@ApiParam(value = "The type company filter.", required = true)
                                                                                @RequestParam FilterOfferType type,
                                                                                @ApiParam(value = "The offer type filter.")
                                                                                @RequestParam(required = false) String filter,
                                                                                @ApiParam(value = "The number of current page.")
                                                                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                                @ApiParam(value = "The number of elements on the page.")
                                                                                @RequestParam(defaultValue = "10", required = false) Integer size,
                                                                                @ApiParam(value = "The field to sort.")
                                                                                @RequestParam(required = false) String sort,
                                                                                @ApiParam(value = "the order.")
                                                                                @RequestParam(defaultValue = "asc", required = false) String order) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyService.getListLightBackOfficeTO(type, filter, page, size, sort, order));
    }

    @ApiOperation(value = "Get relation by company cnpj and user id.")
    @GetMapping(value = "/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<CompanyTO> getRelation(@ApiParam(value = "The company cnpj.", required = true)
                                                 @PathVariable String cnpj,
                                                 @ApiParam(value = "The user id.", required = true)
                                                 @PathVariable @Min(1) long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyUserService.getRelation(cnpj, userId));
    }

    @ApiOperation(value = "Get all companies by user client.")
    @GetMapping(value = "/client/all", produces = "application/json")
    public ResponseEntity<List<CompanyClientTO>> getAllByUserClient() throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.getAllByUserClient());
    }

    @ApiOperation(value = "Get all persons partners by cnpj.")
    @GetMapping(value = "/partners/{cnpj}", produces = "application/json")
    public ResponseEntity<List<PersonPartnerTO>> getAllPartners(@ApiParam(value = "The company cnpj.", required = true)
                                                                @PathVariable String cnpj) throws ResourceNotFoundException {
        return ResponseEntity.ok(PERSON_BINDER.bind(companyService.getAllPartners(cnpj)));
    }

    @ApiOperation(value = "Get company by cnpj.")
    @GetMapping(value = "/{cnpj}", produces = "application/json")
    public ResponseEntity<CompanyClientTO> getCompany(@ApiParam(value = "The company cnpj.", required = true)
                                                      @PathVariable String cnpj) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyUserService.getCompanyClient(cnpj));
    }

    @ApiOperation(value = "Get sessions confirmed of company by cnpj.")
    @GetMapping(value = "/sessions_confirmed/{cnpj}", produces = "application/json")
    public ResponseEntity<SessionConfirmedTO> getSessionConfirmed(@ApiParam(value = "The company cnpj.", required = true)
                                                                  @PathVariable String cnpj) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyUserService.getSessionConfirmed(cnpj));
    }

    @ApiOperation(value = "Get score analysis of company")
    @GetMapping(value = "/score_analysis/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<ScoreSummaryTO> getScoreAnalysis(@ApiParam(value = "The company cnpj.", required = true)
                                                           @PathVariable String cnpj,
                                                           @ApiParam(value = "The user id.", required = true)
                                                           @PathVariable @Min(1) long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyUserService.getScoreAnalysis(cnpj, userId));
    }

    @ApiOperation(value = "Get all companies repeat. only for maintenance")
    @GetMapping(value = "/all_repeat", produces = "application/json")
    public ResponseEntity<List<String>> getAllCnpjRepeat() {
        return ResponseEntity.ok(companyService.getAllCnpjRepeat());
    }

    @ApiOperation(value = "Get all the companies within a radius of location")
    @GetMapping(value = "/radius_location", produces = "application/json")
    public ResponseEntity<List<CompanyLocationTO>> getAllCompaniesInRadioLocation(@ApiParam(value = "The latitude.", required = true)
                                                                                  @RequestParam double latitude,
                                                                                  @ApiParam(value = "The longitude.", required = true)
                                                                                  @RequestParam double longitude,
                                                                                  @ApiParam(value = "the radius in meters. Default 1000")
                                                                                  @RequestParam(defaultValue = "1000") double radius) {
        return ResponseEntity.ok(companyService.getAllCompaniesInRadioLocation(latitude, longitude, radius));
    }

    @ApiOperation(value = "Delete company by id. (only for maintenance)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The company id.", required = true)
                                       @PathVariable Long id) throws ResourceNotFoundException {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete company by cnpj. (only for maintenance)")
    @DeleteMapping(value = "/by_cnpj/{cnpj}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The company cnpj.", required = true)
                                       @PathVariable String cnpj) throws ResourceNotFoundException {
        companyService.delete(cnpj);
        return ResponseEntity.ok().build();
    }

}
