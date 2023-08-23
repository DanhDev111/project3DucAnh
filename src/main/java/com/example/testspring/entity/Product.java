package com.example.testspring.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class Product extends TimeAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private String description;

    private double price;

    private int amount;

    private String image;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductColor> productColors;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_size"
                ,joinColumns = @JoinColumn(name = "product_id")
            ,inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private List<Size> Sizes;

    //1 sản phẩm có nhiều màu(quantity) - trong mỗi mã màu có số lượng khác nhau(quantity)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviewDTOs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comment> commentDTOs;



}
