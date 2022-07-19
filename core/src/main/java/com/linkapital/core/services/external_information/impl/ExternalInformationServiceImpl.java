package com.linkapital.core.services.external_information.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_operation.BankOperationService;
import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.cnd.CndService;
import com.linkapital.core.services.company.datasource.CompanyRepository;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.external_information.ExternalInformationService;
import com.linkapital.core.services.ibge.IbgeService;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.industrial_production_index.PhysicalProductionService;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.integrations.GoogleMapsService;
import com.linkapital.core.services.protest.ProtestService;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.bank_operation.contract.BankOperationBinder.BANK_OPERATION_BINDER;
import static com.linkapital.core.services.company.contract.CompanyBinder.updateCnd;
import static com.linkapital.core.services.company.contract.enums.CompanySector.INDUSTRY;
import static com.linkapital.core.services.company.contract.enums.EmitterType.TCU;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.directory.contract.enums.Type.ARCHIVED_DOCUMENTS;
import static com.linkapital.core.services.directory.contract.enums.Type.CND;
import static com.linkapital.core.services.directory.contract.enums.Type.REGISTRATION_FORM;
import static com.linkapital.core.services.directory.contract.enums.Type.SIMPLIFIED_CERTIFICATION;
import static com.linkapital.core.services.ibge.contract.IbgeBinder.IBGE_BINDER;
import static com.linkapital.core.services.industrial_production_index.contract.PhysicalProductionBinder.PHYSICAL_PRODUCTION_BINDER;
import static com.linkapital.core.services.protest.contract.ProtestBinder.PROTEST_BINDER;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional
public class ExternalInformationServiceImpl implements ExternalInformationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String dataSourceJucep;
    private final boolean activeJucep;
    private final boolean activeIbge;
    private final boolean activeBndes;
    private final boolean activeSidra;
    private final boolean activeCnd;
    private final ProtestService protestService;
    private final GoogleMapsService googleMapsService;
    private final DirectoryService directoryService;
    private final IbgeService ibgeService;
    private final BankOperationService bankOperationService;
    private final PhysicalProductionService physicalProductionService;
    private final CndService cndService;
    private final CompanyRepository companyRepository;

    public ExternalInformationServiceImpl(@Value("${data-source-jucep}") String dataSourceJucep,
                                          @Value("${active-jucep}") boolean activeJucep,
                                          @Value("${active-ibge}") boolean activeIbge,
                                          @Value("${active-bndes}") boolean activeBndes,
                                          @Value("${active-sidra}") boolean activeSidra,
                                          @Value("${active-cnd}") boolean activeCnd,
                                          ProtestService protestService,
                                          GoogleMapsService googleMapsService,
                                          DirectoryService directoryService,
                                          IbgeService ibgeService,
                                          BankOperationService bankOperationService,
                                          PhysicalProductionService physicalProductionService,
                                          CndService cndService,
                                          CompanyRepository companyRepository) {
        this.dataSourceJucep = dataSourceJucep;
        this.activeJucep = activeJucep;
        this.activeIbge = activeIbge;
        this.activeBndes = activeBndes;
        this.activeSidra = activeSidra;
        this.activeCnd = activeCnd;
        this.protestService = protestService;
        this.googleMapsService = googleMapsService;
        this.directoryService = directoryService;
        this.ibgeService = ibgeService;
        this.bankOperationService = bankOperationService;
        this.physicalProductionService = physicalProductionService;
        this.cndService = cndService;
        this.companyRepository = companyRepository;
    }

    @Override
    public void populateExternalInformation(@NonNull Company company) {
        try {
            var address = company.getMainInfo().getAddress();
            if (address != null)
                populateLocation(address);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            populateProtests(company);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            if (activeJucep)
                uploadJucespDocuments(company);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            if (activeIbge)
                populateIbge(company);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            if (activeBndes)
                populateBankOperations(company);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            if (activeSidra)
                populatePhysicalProduction(company);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

//        if (activeCnd)
//            populateCndDocuments(company);
    }

    @Override
    public @NonNull Company uploadJucespDocuments(@NonNull Company company) throws UnprocessableEntityException {
        var socialReason = company.getMainInfo().getSocialReason();
        if (!hasText(socialReason))
            return company;

        if (dataSourceJucep.equals("API"))
            loadDataToCompany(company);
        else if (dataSourceJucep.equals("MOCK"))
            directoryService.uploadJucespDocumentsLocal(company);

        return company;
    }

    @Override
    public ProtestInformationTO updateProtestInformation(String cnpj) throws UnprocessableEntityException {
        var company = getByCnpj(cnpj);
        populateProtests(company);

        return PROTEST_BINDER.bind(companyRepository.saveAndFlush(company).getProtestInformation());
    }

    @Override
    public IbgeTO updateIbge(String cnpj) throws UnprocessableEntityException {
        var company = getByCnpj(cnpj);
        populateIbge(company);

        return IBGE_BINDER.bind(companyRepository.saveAndFlush(company).getIbge());
    }

    @Override
    public List<BankOperationTO> updateBankOperations(String cnpj) throws UnprocessableEntityException {
        var company = getByCnpj(cnpj);
        populateBankOperations(company);

        return BANK_OPERATION_BINDER.bind(companyRepository.saveAndFlush(company).getBankOperations());
    }

    @Override
    public List<PhysicalProductionTO> updatePhysicalProduction(String cnpj) throws UnprocessableEntityException {
        var company = getByCnpj(cnpj);
        populatePhysicalProduction(company);

        return company.getMainCnae() == null
                ? null
                : PHYSICAL_PRODUCTION_BINDER.bind(companyRepository.saveAndFlush(company)
                        .getMainCnae().getPhysicalProductions());
    }

    @Override
    public void populateLocation(Address companyAddress) {
        Optional
                .ofNullable(companyAddress)
                .ifPresent(address -> {
                    if (isEmpty(address.getLatitude())
                            || isEmpty(address.getLongitude())
                            || isEmpty(address.getFormattedAddress())) {
                        var dir = address.getAddress1();

                        if (address.getNumber() != null)
                            dir += "," + address.getNumber();

                        if (address.getOriginalNeighborhood() != null)
                            dir += " " + address.getOriginalNeighborhood();

                        if (address.getMunicipality() != null)
                            dir += " " + address.getMunicipality();

                        googleMapsService.getLocation(dir)
                                .ifPresent(location -> {
                                    address.setLatitude(location.getLatitude());
                                    address.setLongitude(location.getLongitude());
                                    address.setFormattedAddress(location.getFormattedAddress());
                                });
                    }
                });
    }

    @Override
    @Async
    public void populateProtests(@NonNull Company company) throws UnprocessableEntityException {
        var cnpj = company.getMainInfo().getCnpj();
        var protestInformation = protestService.getProtestInformation(cnpj);
        company.setProtestInformation(protestInformation);
    }

    @Override
    @Async
    public void populateIbge(@NonNull Company company) {
        if (company.getIbge() != null)
            return;

        var address = company.getMainInfo().getAddress();
        if (address != null) {
            var ibgeStatistics = ibgeService.getIbge(address.getUf(), address.getMunicipality());
            company.setIbge(ibgeStatistics);
        }
    }

    @Override
    @Async
    public void populateBankOperations(@NonNull Company company) {
        if (!company.getBankOperations().isEmpty())
            return;

        var cnpj = company.getMainInfo().getCnpj();
        var operations = bankOperationService.getBankOperations(cnpj);
        company.getBankOperations().addAll(operations);
    }

    @Override
    @Async
    public void populatePhysicalProduction(@NonNull Company company) {
        var mainCnae = company.getMainCnae();
        if (mainCnae == null)
            return;

        var physicalProductions = mainCnae.getPhysicalProductions();
        if (physicalProductions.isEmpty() && mainCnae.getSector().equals(INDUSTRY))
            physicalProductions.addAll(physicalProductionService.getPhysicalProduction(mainCnae.getCode()));
    }

    @Override
    @Async
    public void populateCndDocuments(@NonNull Company company) {
        Directory document = null;
        try {
            document = getCndTcu(company.getMainInfo().getCnpj());
        } catch (UnprocessableEntityException e) {
            log.error(e.getMessage());
        }

        updateCnd.accept(TCU, document, company);
    }

    @Override
    public List<DirectoryTO> generateJucespDocuments(String cnpj) throws UnprocessableEntityException {
        var company = getByCnpj(cnpj);
        uploadJucepDocuments("", ARCHIVED_DOCUMENTS, company);

        return DIRECTORY_BINDER.bind(companyRepository.saveAndFlush(company).getJucespDocuments());
    }

    private Company getByCnpj(String cnpj) throws UnprocessableEntityException {
        return companyRepository.findByMainInfo_Cnpj(cnpj)
                .orElseThrow(() -> new UnprocessableEntityException(msg("company.cnpj.not.found", cnpj)));
    }

    //region Load Jucesp document (REGISTRATION_FORM, SIMPLIFIED_CERTIFICATION))
    private void loadDataToCompany(Company company) {
        uploadJucepDocuments(msg("company.registration.form.complete"), REGISTRATION_FORM, company);
        uploadJucepDocuments(msg("company.simplified.certificate"), SIMPLIFIED_CERTIFICATION, company);
    }
    //endregion

    //region Upload Jucep Documents for the company
    private void uploadJucepDocuments(String fileName, Type type, Company company) {
        if (REGISTRATION_FORM.equals(type) || SIMPLIFIED_CERTIFICATION.equals(type))
            directoryService.uploadJucepDocumentBot(fileName, type, company);
        else
            directoryService.uploadJucepArchivedDocumentsBot(company);
    }
    //endregion

    //region Returns the document of negative process TCU from the external web with scraping
    private @Nullable Directory getCndTcu(String cnpj) throws UnprocessableEntityException {
        var data = cndService.searchTcu(cnpj);
        return data == null
                ? null
                : directoryService.uploadFile(cnpj, data, CND, "Certificado_negativa_TCU.pdf");
    }
    //endregion

}
