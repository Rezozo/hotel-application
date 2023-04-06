package com.hotel.app.models;

import jakarta.persistence.*;
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
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_seq")
    @SequenceGenerator(name = "room_id_seq", sequenceName = "room_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "type_id")
    private Integer type;
    @Column(name = "room_number")
    private Integer number;
    @Size(max = 50, message = "Error, >50 symbols")
    private String title;
    private String description;
    @Column(name = "image_path")
    @Size(max = 100, message = "Error, >100 symbols")
    private String image;
    private Integer price;
    @Column(name = "room_status")
    private Boolean status;
}
