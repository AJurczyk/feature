package com.ajurczyk.feature.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User does not exist");
    }
}
