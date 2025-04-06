package com.example.autoposter.model;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column()
    private String password;

    @Column(nullable = false)
    private String profilePic;

    @Column(length = 1000)
    private String linkedinAccessToken;

    @Column(length = 1000)
    private String linkedinRefreshToken;

    private LocalDateTime linkedinAccessTokenExpiresAt;

    private LocalDateTime linkedinRefreshTokenExpiresAt;

}
