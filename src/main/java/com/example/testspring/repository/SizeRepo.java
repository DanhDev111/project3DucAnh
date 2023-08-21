package com.example.testspring.repository;

import com.example.testspring.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepo extends JpaRepository<Size,Integer> {

}
