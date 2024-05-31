package com.evri.interview.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<Object> handleCourierNotFoundException(CourierNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalCourierNameException.class)
    public ResponseEntity<Object> handleIllegalCourierNameException(IllegalCourierNameException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleIllegalCourierNameException(Exception ex) {
        return ResponseEntity.internalServerError().build();
    }
}
