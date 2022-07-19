package com.linkapital.core.exceptions;

import com.linkapital.core.util.enums.FieldErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.linkapital.core.util.enums.FieldErrorResponse.ERROR;

@Getter
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class SpedParsingException extends Exception {

    private static final long serialVersionUID = 1L;
    private final FieldErrorResponse field;

    public SpedParsingException(String message) {
        super(message);
        this.field = ERROR;
    }

    public SpedParsingException(String message, FieldErrorResponse field) {
        super(message);
        this.field = field;
    }

}
