package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoDto {
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
    @NotBlank
    private Boolean status;
}
