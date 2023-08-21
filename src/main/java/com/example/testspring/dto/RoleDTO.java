package com.example.testspring.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoleDTO {
    private Integer id;

    @NotBlank
    @Size(min = 6,max = 14)
    private String name;//["ADMIN"],["MEMBER"]
}
