package com.example.testspring.repository;

import com.example.testspring.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepo extends JpaRepository<Color,Integer> {
}
