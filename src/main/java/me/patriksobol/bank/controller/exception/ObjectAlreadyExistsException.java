package me.patriksobol.bank.controller.exception;

import org.springframework.http.HttpStatus;

public class ObjectAlreadyExistsException extends AbstractApplicationException {

    public ObjectAlreadyExistsException(final String message) {
        super(message);
    }

    public ObjectAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
