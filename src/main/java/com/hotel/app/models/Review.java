package com.hotel.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    private Integer id;
    @Column(name = "customer_id")
    private Integer customer;
    @Max(value = 5, message = "Rating must be from 1 to 5")
    @Min(value = 1, message = "Rating must be from 1 to 5")
    private Byte rate;
    private String feedback;
}
