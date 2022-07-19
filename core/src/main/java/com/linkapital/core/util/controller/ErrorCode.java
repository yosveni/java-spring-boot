package com.linkapital.core.util.controller;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static java.util.Arrays.stream;

/**
 * Application error codes. Inspired by {@link org.springframework.http.HttpStatus HttpStatus} enum.
 *
 * @since 0.1.0
 */
public enum ErrorCode {
    // 1xx Validation errors
    INVALID_VALUE(100, "Invalid value"),
    INVALID_FORMAT(101, "Invalid format"),
    INVALID_INPUT(102, "Invalid format"),
    INCONSISTENCY_OPERATION(103, "Inconsistency operation"),
    INVALID_REFERENCE(104, "Invalid reference"),
    ABSENT_FIELD(105, "Absent field"),
    UNDEFINED_VALIDATION(106, "Undefined validation"),

    // 2xx Security errors
    UNAUTHORIZED_ACCESS(200, "Unauthorized"),
    FORBIDDEN_ACCESS(201, "Forbidden"),
    GENERAL_SECURITY_ERROR(202, "General Security Error"),

    // 3xx Backend errors
    INTERNAL_ERROR(300, "Internal Error"),
    NOT_IMPLEMENTED_YET(301, "Not Implemented Yet"),

    // 4xx Backend errors
    NOT_FOUND(400, "Internal Error");

    private final int value;
    private final String detail;

    ErrorCode(int value, String detail) {
        this.value = value;
        this.detail = detail;
    }

    public int getValue() {
        return value;
    }

    public String getDetail() {
        return detail;
    }

    /**
     * Whether this error code is in the Error series {@link com.linkapital.core.util.controller.ErrorCode.Series#VALIDATION VALIDATION}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is1xxValidation() {
        return Series.VALIDATION.equals(series());
    }

    /**
     * Whether this error code is in the Error series {@link com.linkapital.core.util.controller.ErrorCode.Series#SECURITY SECURITY}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is2xxSecurity() {
        return Series.SECURITY.equals(series());
    }

    /**
     * Whether this error code is in the Error series {@link com.linkapital.core.util.controller.ErrorCode.Series#BACKEND BACKEND}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is3xxBackEnd() {
        return Series.SECURITY.equals(series());
    }

    /**
     * Returns the Error Code series of this error code.
     *
     * @see com.linkapital.core.util.controller.ErrorCode.Series
     */
    public Series series() {
        return Series.valueOf(this);
    }

    /**
     * Enumeration of error codes series.
     * <p>Retrievable via {@link com.linkapital.core.util.controller.ErrorCode#series()}.
     */
    public enum Series {
        VALIDATION(1),
        SECURITY(2),
        BACKEND(3);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        public static Series valueOf(int status) {
            int seriesCode = status / 100;
            return stream(values())
                    .filter(series -> series.value == seriesCode)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(msg("error.no.matching.constant.for", status)));
        }

        public static Series valueOf(ErrorCode code) {
            return valueOf(code.value);
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 3.
         */
        public int value() {
            return this.value;
        }
    }
}
