package com.svalero.gestitaller2.exception;

public class ClientNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Cliente no encontrado";

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}