package com.svalero.gestitaller2.exception;

public class BikeNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Moto no encontrada";

    public BikeNotFoundException(String message) {
        super(message);
    }

    public BikeNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}