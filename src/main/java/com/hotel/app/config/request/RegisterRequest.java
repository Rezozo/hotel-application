package com.hotel.app.config.request;

import com.hotel.app.validate.EmailValidator;
import com.hotel.app.validate.PhoneNumberValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Full name is mandatory")
    @Size(min = 10, max = 255, message = "Full name must be between 10 and 255 characters")
    private String fullName;
    @NotBlank(message = "Email is mandatory")
    @Size(min = 7, max = 70, message = "Email must be between 7 and 70 characters")
    @EmailValidator
    private String email;
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 18, message = "Phone number must be between 11 and 18 characters")
    @PhoneNumberValidator
    private String phoneNumber;
    @NotBlank(message = "Password is mandatory")
    private String password;
}

