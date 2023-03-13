package com.example.hotel_management.exception;


public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(final String message) {
        super(message);
    }
}
