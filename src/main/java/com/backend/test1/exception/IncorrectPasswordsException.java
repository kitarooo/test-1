package com.backend.test1.exception;

public class IncorrectPasswordsException extends RuntimeException{
    public IncorrectPasswordsException(String message) {
        super(message);
    }
}
