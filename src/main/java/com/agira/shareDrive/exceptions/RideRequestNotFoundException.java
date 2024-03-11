package com.agira.shareDrive.exceptions;

public class RideRequestNotFoundException extends RuntimeException{
    public RideRequestNotFoundException(String message){
        super(message);
    }
}
