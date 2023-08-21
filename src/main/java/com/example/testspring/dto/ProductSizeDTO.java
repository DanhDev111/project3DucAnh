package com.example.testspring.dto;

import com.example.testspring.entity.Product;
import com.example.testspring.entity.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class ProductSizeDTO {
    private Integer id;

    private Size size;

    private Product product;
}
