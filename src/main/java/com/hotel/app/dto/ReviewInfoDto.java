package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewInfoDto {
    private Integer id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotNull
    private Byte rate;
    @NotBlank
    private String feedback;
}
