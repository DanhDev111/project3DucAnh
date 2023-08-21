package com.example.testspring.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EntityListeners(EntityListeners.class)
@EqualsAndHashCode(callSuper = true)
public class Bill extends TimeAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

//    @CreatedDate()
//    @Column(updatable = false)
//    private Date buyDate;

    private String status;//New,Pending,Active,Delivered

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BillItem> billItems;

}
