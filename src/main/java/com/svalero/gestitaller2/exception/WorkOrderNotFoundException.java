package com.svalero.gestitaller2.exception;

public class WorkOrderNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Orden no encontrada";

    public WorkOrderNotFoundException(String message) {
        super(message);
    }

    public WorkOrderNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}