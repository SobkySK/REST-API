package me.patriksobol.bank.controller.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractApplicationException extends RuntimeException {

    public AbstractApplicationException() {
    }

    public AbstractApplicationException(final String message) {
        super(message);
    }

    public AbstractApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatus();
}
