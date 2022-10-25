package com.stackroute.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException{

    private String message;

    public UserAlreadyExistsException(){

    }
    public UserAlreadyExistsException(String message){
        super();
        this.message = message;
    }

}
