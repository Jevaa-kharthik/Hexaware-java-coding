package com.example.exception;

public class InsuffcientFundsException extends Exception {
    public InsuffcientFundsException(String message) {
        super(message);
    }

    public InsuffcientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
