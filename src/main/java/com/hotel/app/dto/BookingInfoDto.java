package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BookingInfoDto {
    private Integer id;
    @NotBlank(message = "Full name is mandatory")
    @Size(min = 10, max = 255, message = "Full name must be between 10 and 255 characters")
    private String fullName;
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 18, message = "Phone number must be between 11 and 18 characters")
    private String phoneNumber;
    @NotBlank(message = "Room is mandatory")
    private String roomTitle;
    @NotNull(message = "Arrival date is mandatory")
    private Date arrivalDate;
    @NotNull(message = "Departure date is mandatory")
    private Date departureDate;
    private Integer cost;
}
