package com.example.exception;

public class InvalidPetAgeHandling extends Exception {
    public InvalidPetAgeHandling(String message) {
        super(message);
    }

    public InvalidPetAgeHandling(String message, Throwable cause) {
        super(message, cause);
    }
    
}
