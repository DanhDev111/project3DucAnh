package com.example.testspring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.*;
@Data
@Entity
@EntityListeners(EntityListeners.class)
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Nếu xác định mua theo màu sắc thì
    @ManyToOne
    private Product product;

    @ManyToOne
    private Bill bill;

    private int quantity;//Số lượng lúc mua

    private double price; //Giá lúc mua


}
