package com.linkapital.core.services.company_user.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

public class CompanyUserValidator {

    private CompanyUserValidator() {
    }

    //region Validate the offers to accept
    public static void validateSelectedOffer(@NonNull SelectIndicativeOfferTO to) throws UnprocessableEntityException {
        if (!to.isOfferOne() && !to.isOfferTwo() && !to.isOfferThree() && !to.isOfferFour())
            throw new UnprocessableEntityException(msg("company.no.offer.selected.to.confirm"));
    }
    //endregion

    public static void validateLength(@NonNull List<?> list) throws UnprocessableEntityException {
        if (list.isEmpty())
            throw new UnprocessableEntityException(msg("company.files.validation.length"));
    }

    public static void validateLength(MultipartFile @NonNull [] array) throws UnprocessableEntityException {
        if (array.length == 0)
            throw new UnprocessableEntityException(msg("company.files.validation.length"));
    }

    public static void validatePropertyGuaranteeDocumentExist(@NonNull Collection<PropertyGuarantee> propertyGuarantees)
            throws UnprocessableEntityException {
        if (propertyGuarantees
                .stream()
                .anyMatch(propertyGuarantee -> propertyGuarantee.getDocument() == null))
            throw new UnprocessableEntityException(msg("property.guarantee.not.found.document"));
    }

}
