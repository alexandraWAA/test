package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<?> notFoundAds() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
