package com.example.testspring.dto;

import com.example.testspring.entity.Color;
import com.example.testspring.entity.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class ProductColorDTO {
    private Integer id;

    private Product product;

    private Color color;
    //ứng với mỗi màu có 1 số lượng và 1 hình ảnh khác nhau
    // mỗi màu có danh sách(List) hình ảnh khác nhau
    private int quantity;

    private List<String> images; //giả sử chọn file sẽ là images[0],images[1] giống thêm vai trò(role) người dùng
}
