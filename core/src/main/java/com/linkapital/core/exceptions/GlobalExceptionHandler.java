package com.linkapital.core.exceptions;

import com.linkapital.core.services.security.InvalidJwtAuthenticationException;
import com.linkapital.core.util.enums.FieldErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.util.enums.FieldErrorResponse.EMAIL;
import static com.linkapital.core.util.enums.FieldErrorResponse.ERROR;
import static com.linkapital.core.util.enums.FieldErrorResponse.PASSWORD;
import static com.linkapital.core.util.enums.FieldErrorResponse.TOKEN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getMessage(), req.getDescription(false),
                ex.getField()), NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> resourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getMessage(), req.getDescription(false), ERROR),
                CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getCause().getMessage(), req.getDescription(false),
                ERROR), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> unprocessableEntityException(UnprocessableEntityException ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getMessage(), req.getDescription(false),
                ex.getField()), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> integrationAuthExceptionHandler(HttpClientErrorException ex, WebRequest req) {
        return switch (ex.getRawStatusCode()) {
            case 400 -> new ResponseEntity<>(buildErrorDetail(ex.getMessage(),
                    req.getDescription(false), ERROR), BAD_REQUEST);
            case 401 -> new ResponseEntity<>(buildErrorDetail(msg("exception.invalid.password"),
                    req.getDescription(false), PASSWORD), UNAUTHORIZED);
            default -> new ResponseEntity<>(buildErrorDetail(msg("exception.user.not.found"),
                    req.getDescription(false), EMAIL), NOT_FOUND);
        };
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<?> tokenInvalidOrExpired(InvalidJwtAuthenticationException ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getMessage(), req.getDescription(false), TOKEN),
                NOT_FOUND);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<?> tokenInvalidOrExpired(InvalidRefreshTokenException ex, WebRequest req) {
        return new ResponseEntity<>(buildErrorDetail(ex.getMessage(), req.getDescription(false), TOKEN),
                GONE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> globalExceptionHandler(ConstraintViolationException ex, WebRequest req) {
        var msg = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .flatMap(constraintViolation -> StringUtils.hasText(constraintViolation.getMessageTemplate())
                        ? Optional.of(constraintViolation.getMessageTemplate())
                        : Optional.empty())
                .orElse(ex.getMessage());

        return new ResponseEntity<>(buildErrorDetail(msg, req.getDescription(false),
                ERROR), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SystemConfigurationException.class)
    public ResponseEntity<?> invalidConfiguration(SystemConfigurationException ex, WebRequest req) {
        var errorDetails = buildErrorDetail(ex.getMessage(), req.getDescription(false), ERROR);
        return ex instanceof NotFoundSystemConfigurationException ?
                new ResponseEntity<>(errorDetails, NOT_FOUND) :
                new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    private ErrorDetails buildErrorDetail(String message, String details, FieldErrorResponse field) {
        return ErrorDetails
                .builder()
                .message(message)
                .details(details)
                .timestamp(new Date())
                .field(field)
                .build();
    }

}
