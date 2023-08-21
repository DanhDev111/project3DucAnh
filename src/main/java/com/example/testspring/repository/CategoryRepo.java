package com.example.testspring.repository;

import com.example.testspring.entity.Category;
import com.example.testspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

    @Query("SELECT c FROM Category c WHERE c.name LIKE :x")
    Page<Category> searchByName(@Param("x") String s,Pageable pageable);

}
