package com.sachit.credentials.registration.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private String message;
    private String statusCode;
    private LocalDateTime timestamp;

    public ErrorMessage(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }
}
