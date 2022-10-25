package com.stackroute.favoriteservice.exception;

public class BookAlreadyExistsException extends RuntimeException {
    private String message;

    public BookAlreadyExistsException() {}

    public BookAlreadyExistsException(String message) {
        super();
        this.message = message;
    }
}
