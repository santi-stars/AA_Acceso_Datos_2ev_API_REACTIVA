package com.svalero.gestitaller2.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

    private int code;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(int errorCode, String errorMessage) {
        code = errorCode;
        message = errorMessage;
        errors = new HashMap<>();
    }

    public ErrorResponse(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse generalError(int code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(666, "validation ERROR", errors);
    }
}
