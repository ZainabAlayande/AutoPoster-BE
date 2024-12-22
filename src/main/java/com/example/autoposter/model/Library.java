package com.example.autoposter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "content_library")
@ToString
@Getter
@Setter
public class Library {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
