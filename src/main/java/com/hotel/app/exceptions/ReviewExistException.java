package com.hotel.app.exceptions;

public class ReviewExistException extends Exception{
    public ReviewExistException() {
        super("Review already exist");
    }
}
