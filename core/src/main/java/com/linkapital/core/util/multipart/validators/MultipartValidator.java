package com.linkapital.core.util.multipart.validators;

import com.linkapital.core.util.file_analizer.analizers.MultipartFileAnalyzer;
import com.linkapital.core.util.multipart.Multipart;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static io.jsonwebtoken.lang.Strings.hasText;

public class MultipartValidator extends FileValidator implements ConstraintValidator<Multipart, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file != null && !file.isEmpty()) {
            context.disableDefaultConstraintViolation();

            if (hasText(maxSize)) {
                context.buildConstraintViolationWithTemplate(msg(errorMessage.get("maxSize"))).addConstraintViolation();
                valid = DataSize.parse(maxSize).compareTo(DataSize.ofBytes(file.getSize())) > 0;
            }

            if (valid && fileType != null && hasText(fileType.getValue())) {
                context.buildConstraintViolationWithTemplate(msg(errorMessage.get("fileType"))).addConstraintViolation();
                valid = new MultipartFileAnalyzer().isFileType(file, fileType);
            }
        }

        return valid;
    }

}
