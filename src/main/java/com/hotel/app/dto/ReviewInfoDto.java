package com.hotel.app.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewInfoDto {
    private Integer id;
    private String fullName;
    @NotBlank(message = "Email is mandatory")
    @Size(min = 7, max = 70, message = "Email must be between 7 and 70 characters")
    private String email;
    @NotNull(message = "Rate is mandatory")
    @Max(value = 5, message = "Rating must be from 1 to 5")
    @Min(value = 1, message = "Rating must be from 1 to 5")
    private Byte rate;
    private String feedback;
}
