package com.example.exception;

public class FileHandlingException extends Exception {
    public FileHandlingException(String message) {
        super(message);
    }

    public FileHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
