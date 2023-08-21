package com.example.testspring.controller;

import com.example.testspring.dto.*;
import com.example.testspring.repository.CategoryRepo;
import com.example.testspring.repository.SizeRepo;
import com.example.testspring.services.CategoryService;
import com.example.testspring.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/size")
public class SizeController {
    @Autowired
    SizeService sizeService;

    @Autowired
    SizeRepo sizeRepo;

    @PostMapping("/list")
    public ResponseDTO<List<SizeDTO>> readList(@RequestBody SizeDTO sizeDTO){
        List<SizeDTO> sizeDTOS = sizeService.readAll();
        return ResponseDTO.<List<SizeDTO>>builder()
                .status(200)
                .msg("OK")
                .data(sizeDTOS)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newSize(@RequestBody @Valid SizeDTO sizeDTO){
        sizeService.create(sizeDTO);
        return ResponseDTO.<Void>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<SizeDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<SizeDTO>builder()
                .status(200)
                .data(sizeService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<SizeDTO> delete(@PathVariable("id") int id){
        sizeService.delete(id);
        return ResponseDTO.<SizeDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<SizeDTO> update(@RequestBody SizeDTO sizeDTO){
        sizeService.update(sizeDTO);
        return ResponseDTO.<SizeDTO>builder().status(200).msg("OK")
                .data(sizeDTO).build();
    }
}
