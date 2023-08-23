package com.example.testspring.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color color;
    //ứng với mỗi màu có 1 số lượng và 1 hình ảnh khác nhau
    // mỗi màu có danh sách(List) hình ảnh khác nhau
    private int quantity;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images",
            joinColumns = @JoinColumn(name = "product_id"))
    @Column(name ="images")
    private List<String> images; //giả sử chọn file sẽ là images[0],images[1] giống thêm vai trò(role) người dùng
}
