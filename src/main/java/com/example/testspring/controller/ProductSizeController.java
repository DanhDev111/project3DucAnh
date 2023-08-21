package com.example.testspring.controller;

import com.example.testspring.dto.*;
import com.example.testspring.services.ProductService;
import com.example.testspring.services.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/product-size")
public class ProductSizeController {
    @Autowired
    ProductSizeService productService;

    @PostMapping("/list")
    public ResponseDTO<List<ProductSizeDTO>> readAll(){
        List<ProductSizeDTO> productDTOs = productService.readAll();
        return ResponseDTO.<List<ProductSizeDTO>>builder()
                .status(200)
                .msg("OK")
                .data(productDTOs)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<ProductSizeDTO> newProductSize(@ModelAttribute ProductSizeDTO productDTO) throws IOException {
        productService.create(productDTO);
        return ResponseDTO.<ProductSizeDTO>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<ProductSizeDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<ProductSizeDTO>builder()
                .status(200)
                .data(productService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<ProductSizeDTO> delete(@PathVariable("id") int id){
        productService.delete(id);
        return ResponseDTO.<ProductSizeDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<ProductSizeDTO> update(@RequestBody ProductSizeDTO productDTO){
        productService.update(productDTO);
        return ResponseDTO.<ProductSizeDTO>builder().status(200).msg("OK")
                .data(productDTO).build();
    }
}
