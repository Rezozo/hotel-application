package com.hotel.app.validate;


import com.hotel.app.validate.impl.PhoneNumberValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Constraint(validatedBy = PhoneNumberValidatorImpl.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumberValidator {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
