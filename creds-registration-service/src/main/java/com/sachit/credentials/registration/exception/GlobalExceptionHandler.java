package com.sachit.credentials.registration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleOrganizationNotFoundException(OrganizationNotFoundException e) {
        log.error("Inside handleOrganizationNotFoundException: {}",e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CredentialNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCredentialNotFoundException(CredentialNotFoundException e) {
        log.error("Inside handleCredentialNotFoundException: {}",e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException e) {
        log.error("Inside handleUserNotFoundException: {}",e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception e) {
        log.error("Inside handleGlobalException: {}",e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
