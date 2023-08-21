package com.example.testspring.controller;

import com.example.testspring.dto.ColorDTO;
import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.dto.SizeDTO;
import com.example.testspring.repository.ColorRepo;
import com.example.testspring.repository.SizeRepo;
import com.example.testspring.services.ColorService;
import com.example.testspring.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/color")
public class ColorController {
    @Autowired
    ColorService colorService;

    @Autowired
    ColorRepo sizeRepo;

    @PostMapping("/list")
    public ResponseDTO<List<ColorDTO>> readList(@RequestBody ColorDTO colorDTO){
        List<ColorDTO> colorDTOs = colorService.readAll();
        return ResponseDTO.<List<ColorDTO>>builder()
                .status(200)
                .msg("OK")
                .data(colorDTOs)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newColor(@RequestBody @Valid ColorDTO colorDTO){
        colorService.create(colorDTO);
        return ResponseDTO.<Void>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<ColorDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<ColorDTO>builder()
                .status(200)
                .data(colorService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<ColorDTO> delete(@PathVariable("id") int id){
        colorService.delete(id);
        return ResponseDTO.<ColorDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<ColorDTO> update(@RequestBody ColorDTO colorDTO){
        colorService.update(colorDTO);
        return ResponseDTO.<ColorDTO>builder().status(200).msg("OK")
                .data(colorDTO).build();
    }
}
