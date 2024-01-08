package com.example.easyclean.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User does not exist");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
