package com.example.testspring.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(unique = true)
    private String name;

}
