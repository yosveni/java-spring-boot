package com.linkapital.core.services.bank_nomenclature.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclatureUrgency;

import java.util.Set;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static java.util.stream.Collectors.groupingBy;

public class BankNomenclatureValidator {

    private BankNomenclatureValidator() {
    }

    //region Validate no duplicated Urgencies on bank nomenclature
    public static void checkDuplicatedUrgency(Set<BankNomenclatureUrgency> list) throws UnprocessableEntityException {
        if (list
                .stream()
                .collect(groupingBy(BankNomenclatureUrgency::getArea))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue().size() > 1))
            throw new UnprocessableEntityException(msg("bank.nomenclature.urgency.duplicated"));
    }
    //endregion

}
