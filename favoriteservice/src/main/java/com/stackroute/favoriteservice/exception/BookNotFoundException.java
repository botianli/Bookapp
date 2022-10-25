package com.stackroute.favoriteservice.exception;

public class BookNotFoundException extends RuntimeException {
    private String message;

    public BookNotFoundException() {}

    public BookNotFoundException(String message) {
        super();
        this.message = message;
    }
}
