package com.example.testspring.dto;

import com.example.testspring.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {

    private Integer id;

    private String name;

    private String description;

    private String image;//URL


    private double price;


    private int amount;

    private CategoryDTO category;

    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;

    private List<SizeDTO> sizeDTOs;

    private List<ProductColor> productColors;

    private List<ReviewDTO> reviews;

    private List<CommentDTO> comments;

    @JsonIgnore
    private List<MultipartFile> file;
}
