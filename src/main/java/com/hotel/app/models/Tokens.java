package com.hotel.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Tokens {
    @Id
    private Integer id;
    private String token;
    @Column(name = "refresh_token")
    private String refreshToken;
}
