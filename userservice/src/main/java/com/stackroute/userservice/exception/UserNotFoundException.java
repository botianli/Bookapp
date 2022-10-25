package com.stackroute.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    private String message;

    public UserNotFoundException(){

    }
    public UserNotFoundException(String message){
        super();
        this.message = message;
    }
}
