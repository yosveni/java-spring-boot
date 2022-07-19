package com.linkapital.core.resources.company;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyConfirm1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning2TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearningTO;
import com.linkapital.core.services.company_user.contract.to.CompanySpedTO;
import com.linkapital.core.services.company_user.contract.to.DataInitLearning2TO;
import com.linkapital.core.services.company_user.contract.to.InitLearningFourTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningOneTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningThreeTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningTwoTO;
import com.linkapital.core.services.company_user.contract.to.LearningConfirmTO;
import com.linkapital.core.services.company_user.contract.to.UpdateLearningFourTO;
import com.linkapital.core.services.company_user.contract.to.UpdateLearningThreeTO;
import com.linkapital.core.services.sped.contract.to.CreateSpedTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Api(value = "Company Operations", description = "Operations pertaining to the companies and the indicative offer")
@RestController
@RequestMapping("/company")
@Validated
public class CompanyIndicativeOfferResource {

    private final CompanyUserIndicativeOfferService companyUserIndicativeOfferService;

    public CompanyIndicativeOfferResource(CompanyUserIndicativeOfferService companyUserIndicativeOfferService) {
        this.companyUserIndicativeOfferService = companyUserIndicativeOfferService;
    }

    @ApiOperation(value = "Init indicative offer one.")
    @PostMapping(value = "/indicative_offer_one", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning1TO> initLearningOne(@ApiParam(value = "The company cnpj.", required = true)
                                                              @RequestParam String cnpj,
                                                              @ApiParam(value = "The user id.")
                                                              @RequestParam(required = false) @Min(1) Long userId,
                                                              @ApiParam(value = "The credit requested.", required = true)
                                                              @RequestParam @Min(1) double creditRequested,
                                                              @ApiParam(value = "The invoicing informed.", required = true)
                                                              @RequestParam @Min(1) double invoicingInformed,
                                                              @ApiParam(value = "The average reception period in days")
                                                              @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                              @ApiParam(value = "The invoices files to upload.")
                                                              @RequestPart(required = false) MultipartFile invoicesFile,
                                                              @ApiParam(value = "The sped balance file to upload.")
                                                              @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                              @ApiParam(value = "The sped files to upload.")
                                                              @RequestPart(required = false) MultipartFile[] spedFiles,
                                                              @ApiParam(value = "The nfe duplicates files to upload.")
                                                              @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.initLearningOne(
                new InitLearningOneTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withCreditRequested(creditRequested)
                        .withInvoicingInformed(invoicingInformed)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withInvoicesFile(invoicesFile)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Update indicative offer one.")
    @PutMapping(value = "/indicative_offer_one", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning1TO> updateLearningOne(@ApiParam(value = "The company cnpj.", required = true)
                                                                @RequestParam String cnpj,
                                                                @ApiParam(value = "The user id.")
                                                                @RequestParam(required = false) @Min(1) Long userId,
                                                                @ApiParam(value = "The credit requested.", required = true)
                                                                @RequestParam @Min(1) double creditRequested,
                                                                @ApiParam(value = "The invoicing informed.", required = true)
                                                                @RequestParam @Min(1) double invoicingInformed,
                                                                @ApiParam(value = "The average reception period in days")
                                                                @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                                @ApiParam(value = "The invoices files to upload.")
                                                                @RequestPart(required = false) MultipartFile invoicesFile,
                                                                @ApiParam(value = "The sped balance file to upload.")
                                                                @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                                @ApiParam(value = "The sped files to upload.")
                                                                @RequestPart(required = false) MultipartFile[] spedFiles,
                                                                @ApiParam(value = "The nfe duplicates files to upload.")
                                                                @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.updateLearningOne(
                new InitLearningOneTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withCreditRequested(creditRequested)
                        .withInvoicingInformed(invoicingInformed)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withInvoicesFile(invoicesFile)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Init indicative offer two.")
    @PostMapping(value = "/indicative_offer_two", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning2TO> initLearningTwo(@ApiParam(value = "The data to start learning two.", required = true)
                                                              @RequestPart @Valid @NotNull InitLearningTwoTO to,
                                                              @ApiParam(value = "The invoices files to upload.")
                                                              @RequestPart(required = false) MultipartFile invoicesFile,
                                                              @ApiParam(value = "The sped files to upload.")
                                                              @RequestPart(required = false) MultipartFile[] spedFiles) throws UnprocessableEntityException, ResourceNotFoundException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.initLearningTwo(to
                .withInvoicesFile(invoicesFile)
                .withSpedFiles(spedFiles)));
    }

    @ApiOperation(value = "Update indicative offer two.")
    @PutMapping(value = "/indicative_offer_two", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning2TO> updateLearningTwo(@ApiParam(value = "The data to start learning two.", required = true)
                                                                @RequestPart @Valid @NotNull InitLearningTwoTO to,
                                                                @ApiParam(value = "The invoices files to upload.")
                                                                @RequestPart(required = false) MultipartFile invoicesFile,
                                                                @ApiParam(value = "The sped files to upload.")
                                                                @RequestPart(required = false) MultipartFile[] spedFiles) throws UnprocessableEntityException, ResourceNotFoundException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.updateLearningTwo(to
                .withInvoicesFile(invoicesFile)
                .withSpedFiles(spedFiles)));
    }

    @ApiOperation(value = "Init indicative offer three. Upload company INVOICES files (.zip, .rar).")
    @PostMapping(value = "/indicative_offer_three", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning3TO> initLearningThree(@ApiParam(value = "The company cnpj.", required = true)
                                                                @RequestParam String cnpj,
                                                                @ApiParam(value = "The user id.")
                                                                @RequestParam(required = false) @Min(1) Long userId,
                                                                @ApiParam(value = "The average reception period in days")
                                                                @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                                @ApiParam(value = "The compressed file of invoices to upload.")
                                                                @RequestPart(required = false) MultipartFile invoiceFiles,
                                                                @ApiParam(value = "The sped balance file to upload.")
                                                                @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                                @ApiParam(value = "The sped files to upload.")
                                                                @RequestPart(required = false) MultipartFile[] spedFiles,
                                                                @ApiParam(value = "The nfe duplicates files to upload.")
                                                                @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.initLearningThree(
                new InitLearningThreeTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withInvoiceFiles(invoiceFiles)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Update indicative offer three. Upload company INVOICES files (.zip, .rar).")
    @PutMapping(value = "/indicative_offer_three", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning3TO> updateLearningThree(@ApiParam(value = "The company cnpj.", required = true)
                                                                  @RequestParam String cnpj,
                                                                  @ApiParam(value = "The user id.")
                                                                  @RequestParam(required = false) @Min(1) Long userId,
                                                                  @ApiParam(value = "The average reception period in days")
                                                                  @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                                  @ApiParam(value = "Indicates if invoices are replaced", required = true)
                                                                  @RequestParam boolean clean,
                                                                  @ApiParam(value = "The compressed file of invoices to upload.")
                                                                  @RequestPart(required = false) MultipartFile invoiceFiles,
                                                                  @ApiParam(value = "The sped balance file to upload.")
                                                                  @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                                  @ApiParam(value = "The sped files to upload.")
                                                                  @RequestPart(required = false) MultipartFile[] spedFiles,
                                                                  @ApiParam(value = "The nfe duplicates files to upload.")
                                                                  @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.updateLearningThree(
                new UpdateLearningThreeTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withClean(clean)
                        .withInvoiceFiles(invoiceFiles)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Init indicative offer four.")
    @PostMapping(value = "/indicative_offer_four", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanySpedTO> initLearningFour(@ApiParam(value = "The company cnpj.", required = true)
                                                          @RequestParam String cnpj,
                                                          @ApiParam(value = "The user id.")
                                                          @RequestParam(required = false) @Min(1) Long userId,
                                                          @ApiParam(value = "The average reception period in days")
                                                          @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                          @ApiParam(value = "The compressed file of invoices to upload.")
                                                          @RequestPart(required = false) MultipartFile invoiceFiles,
                                                          @ApiParam(value = "The sped balance file to upload.")
                                                          @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                          @ApiParam(value = "The sped files to upload.")
                                                          @RequestPart(required = false) MultipartFile[] spedFiles,
                                                          @ApiParam(value = "The nfe duplicates files to upload.")
                                                          @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.initLearningFour(
                new InitLearningFourTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withInvoicesFile(invoiceFiles)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Update indicative offer four.")
    @PutMapping(value = "/indicative_offer_four", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanySpedTO> updateLearningFour(@ApiParam(value = "The company cnpj.", required = true)
                                                            @RequestParam String cnpj,
                                                            @ApiParam(value = "The user id.")
                                                            @RequestParam(required = false) @Min(1) Long userId,
                                                            @ApiParam(value = "The average reception period in days")
                                                            @RequestParam(required = false, defaultValue = "1") @Min(1) int avgReceiptTerm,
                                                            @ApiParam(value = "Indicates if invoices are replaced", required = true)
                                                            @RequestParam boolean clean,
                                                            @ApiParam(value = "The compressed file of invoices to upload.")
                                                            @RequestPart(required = false) MultipartFile invoiceFiles,
                                                            @ApiParam(value = "The sped balance file to upload.")
                                                            @RequestPart(required = false) MultipartFile spedBalanceFile,
                                                            @ApiParam(value = "The sped files to upload.")
                                                            @RequestPart(required = false) MultipartFile[] spedFiles,
                                                            @ApiParam(value = "The nfe duplicates files to upload.")
                                                            @RequestPart(required = false) MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.updateLearningFour(
                new UpdateLearningFourTO()
                        .withCnpj(cnpj)
                        .withUserId(userId)
                        .withClean(clean)
                        .withAvgReceiptTerm(avgReceiptTerm)
                        .withInvoicesFile(invoiceFiles)
                        .withSpedFiles(spedFiles)
                        .withSpedBalanceFile(spedBalanceFile)
                        .withNfeDuplicatesFiles(nfeDuplicatesFiles)));
    }

    @ApiOperation(value = "Create sped data (indicative offer four, step 2).")
    @PostMapping(value = "/indicative_offer_four/analysis", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyLearning4TO> analysisLearningFour(@ApiParam(value = "The data to create the SPED record.", required = true)
                                                                   @RequestBody @Valid @Validated @NotNull CreateSpedTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.analysisLearningFour(to));
    }

    @ApiOperation(value = "Confirm information of indicative offer one.")
    @PostMapping(value = "/indicative_offer_one/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyClientTO> confirmLearningOne(@ApiParam(value = "The data to confirm information of indicative offer one.", required = true)
                                                              @RequestBody @Valid @NotNull CompanyConfirm1TO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.confirmLearningOne(to));
    }

    @ApiOperation(value = "Confirm information of indicative offer two.")
    @PostMapping(value = "/indicative_offer_two/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyClientTO> confirmLearningTwo(@ApiParam(value = "The data to confirm information of indicative offer two.", required = true)
                                                              @RequestBody @Valid @NotNull LearningConfirmTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.confirmLearningTwo(to));
    }

    @ApiOperation(value = "Confirm information of indicative offer three.")
    @PostMapping(value = "/indicative_offer_three/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyClientTO> confirmLearningThree(@ApiParam(value = "The data to confirm information of indicative offer three.", required = true)
                                                                @RequestBody @Valid @NotNull LearningConfirmTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.confirmLearningThree(to));
    }

    @ApiOperation(value = "Confirm information of indicative offer four.")
    @PostMapping(value = "/indicative_offer_four/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyClientTO> confirmLearningFour(@ApiParam(value = "The data to confirm information of indicative offer four.", required = true)
                                                               @RequestBody @Valid @NotNull LearningConfirmTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.confirmLearningFour(to));
    }

    @ApiOperation(value = "Get indicative offer two.")
    @GetMapping(value = "/indicative_offer_two/{cnpj}", produces = "application/json")
    public ResponseEntity<DataInitLearning2TO> getInitLearningTwo(@ApiParam(value = "The company cnpj.", required = true)
                                                                  @PathVariable String cnpj) throws ResourceNotFoundException, UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.getInitLearningTwo(cnpj));
    }

    @ApiOperation(value = "Get indicative offer one analysis.")
    @GetMapping(value = "/indicative_offer_one", produces = "application/json")
    public ResponseEntity<CompanyLearningTO> getIndicativeOfferAnalysisOne(@ApiParam(value = "The company cnpj.", required = true)
                                                                           @RequestParam String cnpj,
                                                                           @ApiParam(value = "The user id.")
                                                                           @RequestParam(required = false) @Min(1) Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok((CompanyLearningTO) companyUserIndicativeOfferService.getIndicativeOfferAnalysis(cnpj, userId, 1));
    }

    @ApiOperation(value = "Get indicative offer two analysis.")
    @GetMapping(value = "/indicative_offer_two", produces = "application/json")
    public ResponseEntity<CompanyLearning2TO> getIndicativeOfferAnalysisTwo(@ApiParam(value = "The company cnpj.", required = true)
                                                                            @RequestParam String cnpj,
                                                                            @ApiParam(value = "The user id.")
                                                                            @RequestParam(required = false) @Min(1) Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok((CompanyLearning2TO) companyUserIndicativeOfferService.getIndicativeOfferAnalysis(cnpj, userId, 2));
    }

    @ApiOperation(value = "Get indicative offer three analysis.")
    @GetMapping(value = "/indicative_offer_three", produces = "application/json")
    public ResponseEntity<CompanyLearning3TO> getIndicativeOfferAnalysisThree(@ApiParam(value = "The company cnpj.", required = true)
                                                                              @RequestParam String cnpj,
                                                                              @ApiParam(value = "The user id.")
                                                                              @RequestParam(required = false) @Min(1) Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok((CompanyLearning3TO) companyUserIndicativeOfferService.getIndicativeOfferAnalysis(cnpj, userId, 3));
    }

    @ApiOperation(value = "Get indicative offer four analysis.")
    @GetMapping(value = "/indicative_offer_four", produces = "application/json")
    public ResponseEntity<CompanyLearning4TO> getIndicativeOfferAnalysisFour(@ApiParam(value = "The company cnpj.", required = true)
                                                                             @RequestParam String cnpj,
                                                                             @ApiParam(value = "The user id.")
                                                                             @RequestParam(required = false) @Min(1) Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok((CompanyLearning4TO) companyUserIndicativeOfferService.getIndicativeOfferAnalysis(cnpj, userId, 4));
    }

}
