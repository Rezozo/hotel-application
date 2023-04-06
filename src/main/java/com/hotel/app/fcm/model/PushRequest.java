package com.hotel.app.fcm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushRequest {
    private String title;
    private String message;
    private String token;
    public PushRequest() {
        super();
    }
    public PushRequest(String title, String message, String token) {
        super();
        this.title = title;
        this.message = message;
        this.token = token;
    }
}
