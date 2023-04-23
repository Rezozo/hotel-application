package com.hotel.app.exceptions;

public class MaxPeriodException extends Exception {
    public MaxPeriodException() {
        super("Maximum booking period 30 days");
    }
}
