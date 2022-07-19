package com.linkapital.core.util.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static com.linkapital.core.util.controller.ErrorCode.FORBIDDEN_ACCESS;
import static com.linkapital.core.util.controller.ErrorCode.INVALID_REFERENCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

public abstract class Controller<F> {

    private static final ResponseEntity NOT_CONTENT = noContent().build();
    private static final ResponseEntity NOT_FOUND = notFound().build();
    private static final ResponseEntity INTERNAL_ERROR = status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    private static final ResponseEntity FORBIDDEN = status(HttpStatus.FORBIDDEN).build();
    protected @Autowired
    F facade;

    protected <V> ResponseEntity create(Function<F, Result<V>> function) {
        Result<?> result = function.apply(facade);
        return result.isSuccess() ? created(fromCurrentRequest().path("/{id}").buildAndExpand(result.getVal()).toUri()).build()
                : this.getErrorResponse(result.getErrors());
    }

    protected <V> ResponseEntity perform(Function<F, Result<V>> function) {
        Result<?> result = function.apply(facade);
        return result.isSuccess() ? NOT_CONTENT : this.getErrorResponse(result.getErrors());
    }

    protected <V> ResponseEntity get(Function<F, Result<V>> function) {
        Result<?> result = function.apply(facade);
        return result.isSuccess() ? ok(result.getVal()) : this.getErrorResponse(result.getErrors());
    }

    /**
     * Get file from {@link FileResponse}
     *
     * @param function {@link Result}
     * @param <V>
     * @return {@link org.springframework.http.ResponseEntity} with File result
     */
    protected final <V extends FileResponse> ResponseEntity getFile(Result<V> function) {
        return function.isSuccess() ? this.file(function.getVal()) : null;
    }

    //region Get a ResponseEntity from  FileResponse
    private <V extends FileResponse> ResponseEntity file(FileResponse result) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(result.getMediaType().toString()));

        try {
            return new ResponseEntity(result.getFile().readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException var4) {
            var4.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }
    //endregion

    //<editor-fold desc="Support methods">
    protected ResponseEntity getErrorResponse(List<Error> errors) {
        ErrorCode code = errors.stream()
                .map(Error::getCode)
                .reduce(ErrorCode.INTERNAL_ERROR, this::fold);
        return switch (code) {
            case FORBIDDEN_ACCESS -> FORBIDDEN;
            case INVALID_REFERENCE -> NOT_FOUND;
            default -> code.is1xxValidation() ? badRequest().body(errors) : INTERNAL_ERROR;
        };
    }

    private ErrorCode fold(ErrorCode left, ErrorCode right) {
        if (FORBIDDEN_ACCESS == left || FORBIDDEN_ACCESS == right)
            return FORBIDDEN_ACCESS == left ? left : right;

        if (INVALID_REFERENCE == left || INVALID_REFERENCE == right)
            return INVALID_REFERENCE == left ? left : right;

        if (left.is1xxValidation() || right.is1xxValidation())
            return left.is1xxValidation() ? left : right;

        return left;
    }

}
