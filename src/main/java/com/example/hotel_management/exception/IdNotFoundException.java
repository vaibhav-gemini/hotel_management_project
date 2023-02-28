package com.example.hotel_management.exception;


public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message){
        super(message);
    }
}
