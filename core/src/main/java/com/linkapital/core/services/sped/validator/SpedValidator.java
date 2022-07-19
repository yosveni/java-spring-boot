package com.linkapital.core.services.sped.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

public class SpedValidator {

    private SpedValidator() {
    }

    //region Validates that the Multipart list is not empty or does not exceed with more than 3 elements
    public static void validateFiles(MultipartFile[] files) throws UnprocessableEntityException {
        if (files.length == 0) throw new UnprocessableEntityException(msg("sped.file.not.found"));

        if (files.length > 3) throw new UnprocessableEntityException(msg("sped.file.upload.max.quantity"));
    }
    //endregion

    //region Validate that the lists of balances and demonstrations contain elements
    public static void validateLists(LocalDate date, List<?> balances, List<?> demonstrations) throws UnprocessableEntityException {
        if (date.getMonthValue() == 12 && balances.isEmpty())
            throw new UnprocessableEntityException(msg("sped.balance.data.not.found.for.date", date));

        if (demonstrations.isEmpty())
            throw new UnprocessableEntityException(msg("sped.demo.data.not.found.for.date", date));
    }
    //endregion

}
