package com.example.testspring.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer starNumber;

    @Column(name = "review_date",updatable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreatedDate //auto gen new date
    private Date reviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User user;
}
