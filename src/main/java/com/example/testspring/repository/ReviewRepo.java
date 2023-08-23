package com.example.testspring.repository;

import com.example.testspring.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Integer> {
}
