package com.example.testspring.controller;

import com.example.testspring.dto.ProductColorDTO;
import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.repository.ProductRepo;
import com.example.testspring.services.ProductColorService;
import com.example.testspring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/product-color")
public class ProductColorController {
    @Autowired
    ProductColorService productService;

    @PostMapping("/")
    public ResponseDTO<ProductColorDTO> newProduct(@ModelAttribute ProductColorDTO productDTO) throws IOException {
        productService.create(productDTO);
        return ResponseDTO.<ProductColorDTO>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<ProductColorDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<ProductColorDTO>builder()
                .status(200)
                .data(productService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<ProductColorDTO> delete(@PathVariable("id") int id){
        productService.delete(id);
        return ResponseDTO.<ProductColorDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<ProductColorDTO> update(@ModelAttribute ProductColorDTO productDTO){
        productService.update(productDTO);//chuan roi do em quantity = 10 do
        // Em muốn update lại thành màu xanh cái sản phẩm có id =4 mà trong database em thấy nó sai sai
        // e update thì e fai get ra chu id = null thi no them moi thoi
        return ResponseDTO.<ProductColorDTO>builder().status(200).msg("OK")
                .data(productDTO).build();
    }
}
