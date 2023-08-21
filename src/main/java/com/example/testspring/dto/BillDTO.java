package com.example.testspring.dto;

import com.example.testspring.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

//GraphQL tham khảo cái này
@Data
public class BillDTO {
    private Integer id;

    private UserDTO user;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm",timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonManagedReference
    private List<BillItemDTO> billItems;

}

