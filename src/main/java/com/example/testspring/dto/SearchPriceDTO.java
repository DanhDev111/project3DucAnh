package com.example.testspring.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchPriceDTO extends SearchDTO{
    private Integer start;
    private Integer end;
    private Integer categoryId;
}
