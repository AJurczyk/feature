package com.ajurczyk.feature.controller;

import com.ajurczyk.feature.exceptions.FeatureAlreadyExists;
import com.ajurczyk.feature.exceptions.FeatureNotFound;
import com.ajurczyk.feature.exceptions.UserNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Object> handleUserNotFound(UserNotFound ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleFeatureNotFound(FeatureNotFound ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleFeatureExists(FeatureAlreadyExists ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }
}
