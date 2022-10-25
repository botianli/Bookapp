package com.stackroute.userservice.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.userExist}")
    private String userExist;

    @Value(value = "${data.exception.userNotFound}")
    private String userNotFound;

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return new ResponseEntity<String>(userExist, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<String>(userNotFound, HttpStatus.NOT_FOUND);
    }
}
