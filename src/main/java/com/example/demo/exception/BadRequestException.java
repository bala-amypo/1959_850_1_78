package com.example.demo.exception;

public class BadRequestException extends RuntimeException {
    
    /**
     * Constructs a new BadRequestException with the specified detail message.
     * @param message the detail message explaining the bad request
     */
    public BadRequestException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new BadRequestException with the specified detail message and cause.
     * @param message the detail message explaining the bad request
     * @param cause the cause of the exception
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}