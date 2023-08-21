package com.example.testspring.dto;

import com.example.testspring.entity.Product;
import com.example.testspring.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class CommentDTO {
    private int id;
    private String comment;

    private Date commentDate;

    private ProductDTO product;

    @JoinColumn(name = "commenter_id")
    private UserDTO user;
}
