package com.github.gustavopm1.gotcamel.exceptions.movie;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String message){
        super(message);
    }
}
