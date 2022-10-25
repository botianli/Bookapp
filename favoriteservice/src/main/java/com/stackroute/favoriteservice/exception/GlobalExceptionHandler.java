package com.stackroute.favoriteservice.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.errUserNotFound}")
    private String errUserNotFound;

    @Value(value = "${data.exception.errBookNotFound}")
    private String errBookNotFound;

    @Value(value = "${data.exception.errBookAlreadyExists}")
    private String errBookAlreadyExists;

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<String>(errUserNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<String> bookNotFoundException(BookNotFoundException bookNotFoundException) {
        return new ResponseEntity<String>(errBookNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookAlreadyExistsException.class)
    public ResponseEntity<String> bookAlreadyExistsException(BookAlreadyExistsException bookAlreadyExistsException) {
        return new ResponseEntity<String>(errBookAlreadyExists, HttpStatus.CONFLICT);
    }

}
