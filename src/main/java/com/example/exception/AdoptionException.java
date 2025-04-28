package com.example.exception;

public class AdoptionException extends Exception {
    public AdoptionException(String message) {
        super(message);
    }

    public AdoptionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
