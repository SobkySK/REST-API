package me.patriksobol.bank.controller.exception.advice;

import me.patriksobol.bank.controller.exception.AbstractApplicationException;
import me.patriksobol.bank.controller.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(AbstractApplicationException.class)
    protected ResponseEntity<ErrorResponse> handleApplicationException(AbstractApplicationException exception) {
        final ErrorResponse response = new ErrorResponse(exception.getStatus(), exception.getMessage());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        final ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Oops, server error! Please contact staff.");
        exception.printStackTrace();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
