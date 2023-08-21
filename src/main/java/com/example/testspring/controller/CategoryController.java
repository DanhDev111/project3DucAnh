package com.example.testspring.controller;


import com.example.testspring.dto.CategoryDTO;
import com.example.testspring.dto.PageDTO;
import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.dto.SearchDTO;
import com.example.testspring.entity.Category;
import com.example.testspring.repository.CategoryRepo;
import com.example.testspring.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/search")
    public ResponseDTO<PageDTO<CategoryDTO>> search(@RequestBody SearchDTO searchDTO){
        PageDTO<CategoryDTO> categoryDTOS = categoryService.searchByName(searchDTO);
        return ResponseDTO.<PageDTO<CategoryDTO>>builder()
                .status(200)
                .msg("OK")
                .data(categoryDTOS)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return ResponseDTO.<Void>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<CategoryDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<CategoryDTO>builder()
                .status(200)
                .data(categoryService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<CategoryDTO> delete(@PathVariable("id") int id){
        categoryService.delete(id);
        return ResponseDTO.<CategoryDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
       return ResponseDTO.<CategoryDTO>builder().status(200).msg("OK")
                .data(categoryDTO).build();
    }
}
