package com.example.testspring.repository;

import com.example.testspring.entity.Color;
import com.example.testspring.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorRepo extends JpaRepository<ProductColor,Integer> {

}
