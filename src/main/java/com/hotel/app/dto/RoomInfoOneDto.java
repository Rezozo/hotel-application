package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoOneDto {
    private Integer id;
    @NotBlank
    private String type;
    @NotNull
    private Integer number;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String image;
    @NotNull
    private Integer price;
    private List<Timestamp> arrival;
    private List<Timestamp> departure;
    @NotBlank
    private Boolean status;
}
