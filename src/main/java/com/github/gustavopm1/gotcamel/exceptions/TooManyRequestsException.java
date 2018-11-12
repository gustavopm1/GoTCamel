package com.github.gustavopm1.gotcamel.exceptions;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String message){
        super(message);
    }
}
