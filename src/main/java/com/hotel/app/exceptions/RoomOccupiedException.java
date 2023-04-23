package com.hotel.app.exceptions;

public class RoomOccupiedException extends Exception{
    public RoomOccupiedException() {
        super("In this interval the room is occupied");
    }
}
