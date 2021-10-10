package me.patriksobol.bank.controller.model;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class ErrorResponse {

    @NotNull
    private HttpStatus status;

    @NotNull
    private String message;

    public ErrorResponse(
            final HttpStatus status,
            final String message
    ) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
