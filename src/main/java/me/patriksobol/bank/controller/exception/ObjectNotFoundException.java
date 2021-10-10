package me.patriksobol.bank.controller.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends AbstractApplicationException {

    public ObjectNotFoundException(final String message) {
        super(message);
    }

    public ObjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
