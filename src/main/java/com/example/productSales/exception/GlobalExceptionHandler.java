package com.example.productSales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorResponse(LocalDateTime timestamp, int status, String message, String path) {}

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFound ex, WebRequest req) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, WebRequest req) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        throw ex;
    }

    @ExceptionHandler(AuthenticationException.class)
    public void handleAuthenticationException(org.springframework.security.core.AuthenticationException ex) {
        throw ex;
    }
}
