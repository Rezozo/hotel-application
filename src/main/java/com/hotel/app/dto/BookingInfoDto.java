package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BookingInfoDto {
    private Integer id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String roomTitle;
    @NotBlank
    private Timestamp arrivalDate;
    @NotBlank
    private Timestamp departureDate;
    @NotNull
    private Integer cost;
}
