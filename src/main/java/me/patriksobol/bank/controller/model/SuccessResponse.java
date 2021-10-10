package me.patriksobol.bank.controller.model;

import javax.validation.constraints.NotNull;

public class SuccessResponse {

    @NotNull
    private String message;

    public SuccessResponse(
            final String message
    ) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
