package com.hotel.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_seq")
    @SequenceGenerator(name = "booking_id_seq", sequenceName = "booking_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "customer_id")
    private Integer customer;
    @Column(name = "room_id")
    private Integer room;
    @Column(name = "arrival_date")
    private Timestamp arrival;
    @Column(name = "departure_date")
    private Timestamp departure;
    @Column(name = "total_cost")
    private Integer cost;
}
