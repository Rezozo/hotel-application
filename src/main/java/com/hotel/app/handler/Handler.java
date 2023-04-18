package com.hotel.app.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNotValidException(MethodArgumentNotValidException ex) {
        String errMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest().body(errMessage);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoElementException(NoSuchElementException ex) {
        String errMessage = ex.getMessage();
        return ResponseEntity.badRequest().body(errMessage);
    }
}
