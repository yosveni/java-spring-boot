package com.linkapital.core.services.company.impl;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyMaintenanceService;
import com.linkapital.core.services.company.CompanyPartnersService;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.CompanyRepository;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.datasource.CompanyUserRepository;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.external_information.ExternalInformationService;
import com.linkapital.core.services.security.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class CompanyMaintenanceServiceImpl implements CompanyMaintenanceService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;
    private final CompanyPartnersService companyPartnersService;
    private final UserService userService;
    private final ExternalInformationService externalInformationService;
    private final DirectoryService directoryService;

    public CompanyMaintenanceServiceImpl(CompanyUserRepository companyUserRepository,
                                         CompanyRepository companyRepository,
                                         CompanyPartnersService companyPartnersService,
                                         CompanyService companyService,
                                         UserService userService,
                                         ExternalInformationService externalInformationService,
                                         DirectoryService directoryService) {
        this.companyUserRepository = companyUserRepository;
        this.companyRepository = companyRepository;
        this.companyPartnersService = companyPartnersService;
        this.companyService = companyService;
        this.userService = userService;
        this.externalInformationService = externalInformationService;
        this.directoryService = directoryService;
    }

    @Override
    public void updateForMaintenance(String cnpj) throws UnprocessableEntityException {
        try {
            maintenance(companyService.getByCnpj(cnpj));
        } catch (ResourceNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    @Override
    public void updateAllMaintenance() throws UnprocessableEntityException {
        var companies = companyRepository.findAllByClient(true);
        for (Company company : companies)
            maintenance(company);
    }

    @Override
    public void deleteCompanyUserId(long id) {
        companyUserRepository.findById(id).ifPresent(companyUser -> {
            var user = companyUser.getUser();
            user.getCompanies().removeIf(c -> c.getId().equals(id));
            userService.save(user);

            var company = companyUser.getCompany();
            company.getCompanyUsers().removeIf(cu -> cu.getId().equals(id));
            companyService.save(company);
        });
        companyUserRepository.deleteById(id);
    }

    @Override
    public void deleteCompanyById(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void deleteAllNotClient() {
        var companies = companyRepository.findAllByClient(false);
        companyRepository.deleteAll(companies);
    }

    @Override
    public void deleteCompanyUser(long id) throws UnprocessableEntityException {
        var user = userService.getById(id);
        user.getCompanies().removeIf(companyUser -> {
            if (companyUser.getUser().getId().equals(id)) {
                var company = companyUser.getCompany();
                company.getCompanyUsers().removeIf(cu -> cu.getUser().getId().equals(id));
                userService.save(user);
                companyService.save(company);

                return true;
            }

            return false;
        });
    }

    @Override
    public void deleteAllCompany() {
        var companies = companyService.getAll();
        companies.forEach(company -> company.getCompanyUsers().removeIf(companyUser -> {
            if (companyUser.getCompany().getId().equals(company.getId())) {
                var user = companyUser.getUser();
                user.getCompanies().removeIf(cu -> cu.getCompany().getId().equals(company.getId()));
                userService.save(user);
                companyService.save(company);

                return true;
            }

            directoryService.deleteFiles(company.getJucespDocuments());
            return false;
        }));
        companyRepository.deleteAll();
    }

    private void maintenance(@NonNull Company company) throws UnprocessableEntityException {
        //Paso 1 Eliminar directo por la bd las tuplas de las relacionadas, beneficiarios y partners
        //Paso 2
        company.getCnaes().clear();
        company.getAffiliates().removeIf(companyMainInfo -> {
            var data = companyRepository.findByMainInfoOrMatrixInfo(companyMainInfo.getId());
            if (data.isEmpty())
                return true;

            var canDelete = new AtomicBoolean(true);
            data.forEach(c -> {
                if (c.isClient())
                    canDelete.set(false);
                else
                    companyRepository.delete(c);
            });

            return canDelete.get();
        });

        var matrix = company.getMatrixInfo();
        if (matrix != null) {
            var list = companyRepository.findByMainInfoOrMatrixInfo(matrix.getId());
            if (list.isEmpty())
                company.setMatrixInfo(null);

            var canDelete = new AtomicBoolean(true);
            list.forEach(c -> {
                if (c.isClient())
                    canDelete.set(false);
                else
                    companyRepository.delete(c);
            });

            if (canDelete.get())
                company.setMatrixInfo(null);
        }

        //Paso 3...Esto se comentarea para la primera llamada, luego q se haga la primera limpieza se ejecuta el lo de
        // arriba comentareado y este codigo solamente.
        companyPartnersService.deleteAll(company.getPartnersOf());
        company.getPartnersOf().clear();
        companyPartnersService.deleteAll(company.getPartners());
        company.getPartners().clear();

        company = companyService.save(company);
        company = companyService.getFromExternalApi(company.getMainInfo().getCnpj(), company);

        externalInformationService.populateLocation(company.getMainInfo().getAddress());
        externalInformationService.populateIbge(company);
        externalInformationService.populateBankOperations(company);
        companyService.save(company);
    }

}
