package com.example.testspring.repository;

import com.example.testspring.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query("SELECT c FROM Role c WHERE c.name LIKE :x ")
    Page<Role> searchByName(@Param("x") String s, Pageable pageable);

    Role findByName(String name);
}
