package com.hotel.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    private Integer id;
    @Column (name = "full_name")
    @Size(max = 255, message = "Error, >255 symbols")
    private String fullName;
    @Size(max = 70, message = "Error, >70 symbols")
    private String email;
    @Column (name = "phone_number")
    @Size(max = 18, message = "Error, >18 symbols")
    private String phoneNumber;
}
