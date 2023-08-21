package com.example.testspring.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

}
