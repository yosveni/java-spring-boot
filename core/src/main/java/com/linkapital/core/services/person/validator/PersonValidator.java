package com.linkapital.core.services.person.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

public class PersonValidator {

    private PersonValidator() {
    }

    //region Validates IRPF files
    public static void validateIrpfFiles(MultipartFile[] fileIrpf, MultipartFile[] fileIrpfReceipt) throws UnprocessableEntityException {
        if (fileIrpf.length == 0)
            throw new UnprocessableEntityException(msg("person.irpf.file.required"));

        if (fileIrpfReceipt.length == 0)
            throw new UnprocessableEntityException(msg("person.irpf.receipt.file.required"));
    }
    //endregion
}
