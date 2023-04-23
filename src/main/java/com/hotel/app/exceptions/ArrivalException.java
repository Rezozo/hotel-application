package com.hotel.app.exceptions;

public class ArrivalException extends Exception{
    public ArrivalException() {
        super("Arrival date cannot be bigger than date of departure");
    }
}
