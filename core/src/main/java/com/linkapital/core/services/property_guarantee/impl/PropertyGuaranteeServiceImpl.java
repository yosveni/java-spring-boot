package com.linkapital.core.services.property_guarantee.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.property_guarantee.PropertyGuaranteeService;
import com.linkapital.core.services.property_guarantee.contract.to.CreatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.UpdatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.datasource.PropertyGuaranteeRepository;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.shared.datasource.AddressRepository;
import com.linkapital.core.services.shared.datasource.PropertyRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.directory.contract.enums.Type.GUARANTEE_PROPERTY;
import static com.linkapital.core.services.property_guarantee.contract.PropertyGuaranteeBinder.PROPERTY_GUARANTEE_BINDER;
import static java.util.Comparator.comparing;

@Service
@Transactional
public class PropertyGuaranteeServiceImpl implements PropertyGuaranteeService {

    private final PropertyGuaranteeRepository propertyGuaranteeRepository;
    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;
    private final CompanyUserService companyUserService;
    private final DirectoryService directoryService;

    public PropertyGuaranteeServiceImpl(PropertyGuaranteeRepository propertyGuaranteeRepository,
                                        PropertyRepository propertyRepository,
                                        AddressRepository addressRepository,
                                        CompanyUserService companyUserService,
                                        DirectoryService directoryService) {
        this.propertyGuaranteeRepository = propertyGuaranteeRepository;
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.companyUserService = companyUserService;
        this.directoryService = directoryService;
    }

    @Override
    public PropertyGuaranteeTO create(CreatePropertyGuaranteeTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), null);
        var propertyGuarantee = PROPERTY_GUARANTEE_BINDER.bind(to)
                .withCompanyUser(companyUser);
        companyUser.getPropertyGuarantees().add(propertyGuarantee);
        var guarantees = companyUserService.save(companyUser)
                .getPropertyGuarantees()
                .stream()
                .sorted(comparing(PropertyGuarantee::getId))
                .toList();

        return PROPERTY_GUARANTEE_BINDER.bind(guarantees.get(guarantees.size() - 1));
    }

    @Override
    public PropertyGuaranteeTO update(UpdatePropertyGuaranteeTO to) throws UnprocessableEntityException {
        var propertyGuarantee = PROPERTY_GUARANTEE_BINDER.set(to, getById(to.getId()));
        return PROPERTY_GUARANTEE_BINDER.bind(propertyGuaranteeRepository.save(propertyGuarantee));
    }

    @Override
    public PropertyGuaranteeTO uploadDocument(long id, MultipartFile file) throws UnprocessableEntityException {
        var propertyGuarantee = getById(id);
        var document = directoryService.uploadFile(RandomString.make(), file, GUARANTEE_PROPERTY);
        return PROPERTY_GUARANTEE_BINDER.bind(propertyGuaranteeRepository.save(propertyGuarantee
                .withDocument(document)));
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(propertyGuarantee -> {
                    var companyUser = propertyGuarantee.getCompanyUser();
                    companyUser.getPropertyGuarantees().removeIf(pg -> {
                        if (pg.getId().equals(propertyGuarantee.getId())) {
                            var document = propertyGuarantee.getDocument();
                            if (document != null)
                                directoryService.delete(document.getUrl());

                            var address = propertyGuarantee.getAddress();
                            if (!propertyRepository.existsByAddress_Id(address.getId()))
                                addressRepository.delete(address);

                            return true;
                        }

                        return false;
                    });
                    companyUserService.save(companyUser);
                });
    }

    @Override
    public void deleteDocument(long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(propertyGuarantee -> {
                    directoryService.delete(propertyGuarantee.getDocument().getUrl());
                    propertyGuaranteeRepository.save(propertyGuarantee
                            .withDocument(null));
                });
    }

    //region Get property guarantee by id
    private PropertyGuarantee getById(long id) throws UnprocessableEntityException {
        return propertyGuaranteeRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("property.guarantee.not.found", id)));
    }
    //endregion

}
