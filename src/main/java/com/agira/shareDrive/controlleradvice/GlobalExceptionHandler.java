package com.agira.shareDrive.controlleradvice;

import com.agira.shareDrive.exceptions.RideNotFoundException;
import com.agira.shareDrive.exceptions.RideRequestNotFoundException;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RideRequestNotFoundException.class)
    public ResponseEntity<String> rideRequestNotFoundException(RideRequestNotFoundException rideRequestNotFoundException){
        return new ResponseEntity<>(rideRequestNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<String> rideNotFoundException(RideNotFoundException rideNotFoundException){
        return new ResponseEntity<>(rideNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String message = "Method '" + ex.getMethod() + "' is not supported for this endpoint.";
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runTimeException(RuntimeException runtimeException){
        return new ResponseEntity<>(runtimeException.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
    }
}
