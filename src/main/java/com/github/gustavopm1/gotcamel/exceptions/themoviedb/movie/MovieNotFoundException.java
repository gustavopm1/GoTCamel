package com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String message){
        super(message);
    }
}
