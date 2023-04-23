package com.hotel.app.exceptions;

public class DatePastException extends Exception {
    public DatePastException() {
        super("Date is past");
    }
}
