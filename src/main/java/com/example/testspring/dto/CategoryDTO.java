package com.example.testspring.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {
    private int id;

    @NotBlank(message = "{not.blank}")
    private String name;
}
