package com.flaco.skateschool.exception;

// Custom exception for Bad Request scenarios
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}