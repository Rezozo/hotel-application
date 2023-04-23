package com.hotel.app.fcm.model

data class PushRequest(val title: String, val message: String, val token: String)