package com.example.testspring.repository;

import com.example.testspring.dto.ProductDTO;
import com.example.testspring.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE :name")
    Page<Product> searchByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT p from Product p join p.category c where c.id = :categoryId OR p.category.name =:categoryId")
    Page<Product> searchByCategory(@Param("categoryId") int categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price >= :start AND p.price <= :end")
    Page<Product> searchByPrice(@Param("start") int start, @Param("end") int end, Pageable pageable);
}
