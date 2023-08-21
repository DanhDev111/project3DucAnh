package com.example.testspring.repository;

import com.example.testspring.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepo extends JpaRepository<ProductSize,Integer> {

}
