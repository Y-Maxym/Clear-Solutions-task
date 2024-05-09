package com.spring.maxym.clearsolutionstask.exception;

public class UserNotFoundException extends SimpleApplicationException {

    public UserNotFoundException() {
        super("User not found");
    }
}
