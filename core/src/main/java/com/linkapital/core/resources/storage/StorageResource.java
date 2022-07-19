package com.linkapital.core.resources.storage;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.commission.CommissionService;
import com.linkapital.core.services.commission.contract.to.get.CommissionInstallmentTO;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.contract.to.DocumentCompanyTO;
import com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.external_information.ExternalInformationService;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer_installment.OfferInstallmentService;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.contract.to.IrpfDocumentsTO;
import com.linkapital.core.services.property_guarantee.PropertyGuaranteeService;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.sped.SpedFileService;
import com.linkapital.core.util.MimeTypes;
import com.linkapital.core.util.multipart.Multipart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Api(value = "Storage Operations.", description = "Operations pertaining to the storage.")
@RestController
@RequestMapping("/storage")
public class StorageResource {

    private static final String MAX_FILE_SIZE = "10MB";
    private static final String MAX_ARRAY_FILE_SIZE = "50MB";
    private final DirectoryService directoryService;
    private final CompanyService companyService;
    private final CompanyUserService companyUserService;
    private final CompanyUserIndicativeOfferService companyUserIndicativeOfferService;
    private final OfferService offerService;
    private final SpedFileService spedFileService;
    private final OfferInstallmentService offerInstallmentService;
    private final PropertyGuaranteeService propertyGuaranteeService;
    private final UserService userService;
    private final CommissionService commissionService;
    private final PersonService personService;
    private final ExternalInformationService externalInformationService;

    public StorageResource(DirectoryService directoryService,
                           CompanyService companyService,
                           CompanyUserService companyUserService,
                           CompanyUserIndicativeOfferService companyUserIndicativeOfferService,
                           OfferService offerService,
                           SpedFileService spedFileService,
                           OfferInstallmentService offerInstallmentService,
                           PropertyGuaranteeService propertyGuaranteeService,
                           UserService userService,
                           CommissionService commissionService,
                           PersonService personService,
                           ExternalInformationService externalInformationService) {
        this.directoryService = directoryService;
        this.companyService = companyService;
        this.companyUserService = companyUserService;
        this.companyUserIndicativeOfferService = companyUserIndicativeOfferService;
        this.offerService = offerService;
        this.spedFileService = spedFileService;
        this.offerInstallmentService = offerInstallmentService;
        this.propertyGuaranteeService = propertyGuaranteeService;
        this.userService = userService;
        this.commissionService = commissionService;
        this.personService = personService;
        this.externalInformationService = externalInformationService;
    }

    @ApiOperation(value = "Load file.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> load(@ApiParam(value = "The file name.", required = true)
                                                    @RequestParam String name,
                                                    @ApiParam(value = "The file ext.", required = true)
                                                    @RequestParam String ext,
                                                    @ApiParam(value = "The file url.", required = true)
                                                    @RequestParam String url) throws UnprocessableEntityException {
        return ResponseEntity.ok(
                Map.of("file", Base64.encodeBase64String(directoryService.loadFile(url)),
                        "type", MimeTypes.getMimeType(ext),
                        "name", name));
    }

    @ApiOperation(value = "Download Excel SPED Model.")
    @GetMapping(value = "/download/sped/model")
    public void downloadSpedBalanceModel(HttpServletResponse response) throws UnprocessableEntityException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + format("Modelo SPED %s.xlsx", randomUUID()) + "\"");
        spedFileService.generateSpedBalanceModel(response);
    }

    @ApiOperation(value = "Documents uploaded in the comments of offer.")
    @GetMapping(value = "/offer/comments/documents/{offerId}", produces = "application/json")
    public ResponseEntity<List<DirectoryTO>> getDocumentsForCommentsOffer(@ApiParam(value = "The offer id.", required = true)
                                                                          @PathVariable @Min(1) long offerId) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.getDocumentsForCommentsOffer(offerId));
    }

    @ApiOperation(value = "Documents company.")
    @GetMapping(value = "/documents/{cnpj}/{userId}", produces = "application/json")
    public ResponseEntity<DocumentCompanyTO> getDocumentsCompany(@ApiParam(value = "The company cnpj.", required = true)
                                                                 @PathVariable String cnpj,
                                                                 @ApiParam(value = "The user id.", required = true)
                                                                 @PathVariable @Min(1) Long userId) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.getDocumentsCompany(cnpj, userId));
    }

    @ApiOperation(value = "Upload company file.")
    @PostMapping(value = "/upload/cnpj", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<List<LightBackOfficeTO>> saveCnpjsFile(@ApiParam(value = "The file to upload with an 'xls' or 'xlsx' extension.", required = true)
                                                                 @RequestPart @Multipart(maxSize = MAX_FILE_SIZE) MultipartFile file) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.saveCnpjsFile(file));
    }

    @ApiOperation(value = "Upload DEBIT documents for a company by cnpj.")
    @PostMapping(value = "/upload/debits/{cnpj}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<List<DirectoryTO>> uploadDebitsDocuments(@ApiParam(value = "The company cnpj.", required = true)
                                                                   @PathVariable String cnpj,
                                                                   @ApiParam(value = "The files to upload.", required = true)
                                                                   @RequestPart @Multipart(maxSize = MAX_ARRAY_FILE_SIZE) MultipartFile[] files) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.uploadDebitsDocuments(cnpj, files));
    }

    @ApiOperation(value = "Upload BANK documents for a company by cnpj.")
    @PostMapping(value = "/upload/bank/{cnpj}/{userId}/{id}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyBankDocumentTO> uploadBankDocuments(@ApiParam(value = "The company cnpj.", required = true)
                                                                     @PathVariable String cnpj,
                                                                     @ApiParam(value = "The user id.", required = true)
                                                                     @PathVariable @Min(1) long userId,
                                                                     @ApiParam(value = "The bank nomenclature id.", required = true)
                                                                     @PathVariable @Min(1) long id,
                                                                     @ApiParam(value = "The files to upload.", required = true)
                                                                     @RequestPart @Multipart(maxSize = MAX_ARRAY_FILE_SIZE) MultipartFile[] files) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserService.uploadBankDocuments(cnpj, userId, id, files));
    }

    @ApiOperation(value = "Upload SPED Document (TXT).")
    @PostMapping(value = "/upload/sped", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning4TO> uploadTxtSped(@ApiParam(value = "The company cnpj.", required = true)
                                                            @RequestParam String cnpj,
                                                            @ApiParam(value = "The user id.")
                                                            @RequestParam(required = false) @Min(1) Long userId,
                                                            @ApiParam(value = "The sped files to upload.", required = true)
                                                            @RequestPart MultipartFile[] files) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.uploadTxtSped(cnpj, userId, files));
    }

    @ApiOperation(value = "Upload SPED Document (Excel).")
    @PostMapping(value = "/upload/sped/model", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CompanyLearning4TO> uploadExcelSped(@ApiParam(value = "The company cnpj.", required = true)
                                                              @RequestParam String cnpj,
                                                              @ApiParam(value = "The user id.")
                                                              @RequestParam(required = false) @Min(1) Long userId,
                                                              @ApiParam(value = "The excel to upload.", required = true)
                                                              @RequestPart MultipartFile file) throws UnprocessableEntityException {
        return ResponseEntity.ok(companyUserIndicativeOfferService.uploadExcelSped(cnpj, userId, file));
    }

    @ApiOperation(value = "Upload offer contract")
    @PostMapping(value = "/upload/offer_contract", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<OfferTO> uploadOfferContract(@ApiParam(value = "The offer id.", required = true)
                                                       @RequestParam @Min(1) long offerId,
                                                       @ApiParam(value = "The date of the contract.", required = true)
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date contractDate,
                                                       @ApiParam(value = "The signed contract.", required = true)
                                                       @RequestPart @Multipart(maxSize = MAX_ARRAY_FILE_SIZE) MultipartFile[] files) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.uploadOfferContract(offerId, contractDate, files));
    }

    @ApiOperation(value = "Generate all jucesp documents for a company by cnpj.")
    @PostMapping(value = "/generate_document/{cnpj}")
    public ResponseEntity<List<DirectoryTO>> generateJucespDocuments(@ApiParam(value = "The company cnpj.", required = true)
                                                                     @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(externalInformationService.generateJucespDocuments(cnpj));
    }

    @ApiOperation(value = "Upload payment ticket document")
    @PostMapping(value = "/upload/payment_ticket", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<OfferInstallmentTO> uploadPaymentTicket(@ApiParam(value = "The offer installment id.", required = true)
                                                                  @RequestParam @Min(1) long offerInstallmentId,
                                                                  @ApiParam(value = "the voucher or payment slip.", required = true)
                                                                  @RequestPart @Multipart(maxSize = MAX_ARRAY_FILE_SIZE) @NotNull MultipartFile file) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerInstallmentService.uploadPaymentTicket(offerInstallmentId, file));
    }

    @ApiOperation(value = "Upload property guarantee document.")
    @PostMapping(value = "/upload/property_guarantee/{id}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<PropertyGuaranteeTO> uploadPropertyGuaranteeDocument(@ApiParam(value = "The property guarantee id.", required = true)
                                                                               @PathVariable @Min(1) long id,
                                                                               @ApiParam(value = "The file to upload.")
                                                                               @RequestPart MultipartFile file) throws UnprocessableEntityException {
        return ResponseEntity.ok(propertyGuaranteeService.uploadDocument(id, file));
    }

    @ApiOperation(value = "Upload user image.")
    @PostMapping(value = "/upload/user_image", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<UserTO> uploadUserImage(@ApiParam(value = "The image to upload.", required = true)
                                                  @RequestPart @Multipart(maxSize = "1MB") MultipartFile file) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.uploadImage(file));
    }

    @ApiOperation(value = "Upload fiscal note to commissions installments.")
    @PostMapping(value = "/upload/commission_installment", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<List<CommissionInstallmentTO>> uploadFiscalTaxNoteToCommissionInstallments(@ApiParam(value = "The commissions installments ids.", required = true)
                                                                                                     @RequestParam @NotEmpty List<Long> commissionInstallmentIds,
                                                                                                     @ApiParam(value = "The fiscal note.", required = true)
                                                                                                     @RequestPart @Multipart(maxSize = MAX_ARRAY_FILE_SIZE) MultipartFile file) {
        return ResponseEntity.ok(commissionService.uploadFiscalNotesToCommissionInstalments(commissionInstallmentIds, file));
    }

    @ApiOperation(value = "Upload IRPF files.")
    @PostMapping(value = "/upload/irpf", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<IrpfDocumentsTO> uploadIrpf(@ApiParam(value = "The cpf of the partner or spouse.", required = true)
                                                      @RequestParam String cpf,
                                                      @ApiParam(value = "Indicates if the person is partner.", required = true)
                                                      @RequestParam boolean isPartner,
                                                      @ApiParam(value = "The files IRPF to upload.", required = true)
                                                      @RequestPart MultipartFile[] filesIrpf,
                                                      @ApiParam(value = "The files IRPF RECEIPT to upload.", required = true)
                                                      @RequestPart MultipartFile[] filesIrpfReceipt) throws ResourceNotFoundException, UnprocessableEntityException {
        return ResponseEntity.ok(personService.uploadFile(cpf, filesIrpf, filesIrpfReceipt, isPartner));
    }


}
