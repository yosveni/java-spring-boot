package com.linkapital.core.services.company.datasource;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.offer.contract.enums.FilterOfferType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    Page<CompanyUser> getByFilterGlobal(FilterOfferType type, String filter, Pageable pageable, String sort, String order) throws UnprocessableEntityException;

}
