package com.example.testspring.controller;


import com.example.testspring.dto.*;
import com.example.testspring.entity.Category;
import com.example.testspring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/search")
    public ResponseDTO<PageDTO<ProductDTO>> search(@RequestBody SearchPriceDTO searchDTO){
        PageDTO<ProductDTO> productDTOs = productService.searchName(searchDTO);
        return ResponseDTO.<PageDTO<ProductDTO>>builder()
                .status(200)
                .msg("OK")
                .data(productDTOs)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<ProductDTO> newProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
        productService.create(productDTO);
        return ResponseDTO.<ProductDTO>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<ProductDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<ProductDTO>builder()
                .status(200)
                .data(productService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<ProductDTO> delete(@PathVariable("id") int id){
        productService.delete(id);
        return ResponseDTO.<ProductDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<ProductDTO> update(@ModelAttribute ProductDTO productDTO){
        productService.update(productDTO);//chuan roi do em quantity = 10 do
        // Em muốn update lại thành màu xanh cái sản phẩm có id =4 mà trong database em thấy nó sai sai
        // e update thì e fai get ra chu id = null thi no them moi thoi
        return ResponseDTO.<ProductDTO>builder().status(200).msg("OK")
                .data(productDTO).build();
    }
}
