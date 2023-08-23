package com.example.testspring.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchBillDateDTO extends SearchDTO{

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
}
