package com.agroguide.guia.domain.exception;

public class GuiaNoExisteException extends RuntimeException {
    public GuiaNoExisteException(String message) {
        super(message);
    }
}
