package com.agira.shareDrive.controlleradvice;

import com.agira.shareDrive.exceptions.RideRequestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RideRequestNotFoundException.class)
    public ResponseEntity<String> rideRequestNotFoundException(RideRequestNotFoundException rideRequestNotFoundException){
        return new ResponseEntity<>(rideRequestNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
