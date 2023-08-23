package com.example.testspring.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BillItemDTO {
    private Integer id;

    //	@JsonBackReference
    //Mình sẽ không lấy cái attr
    // ở bên private List<BillItemDTO> billItems;
    @JsonIgnoreProperties("billItems")
    private BillDTO billDTO;

    private ProductDTO product;

    @Min(0)
    private int quantity;
    @Min(0)
    private double price;
}
