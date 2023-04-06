package com.hotel.app.enums;

public enum Role {
    Admin(1),
    User(2),
    Manager(4);

    private final int value;
    Role(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
