package com.example.testspring.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(EntityListeners.class)
@Data
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    private Size size;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
