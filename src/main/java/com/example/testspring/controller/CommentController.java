package com.example.testspring.controller;

import com.example.testspring.dto.*;
import com.example.testspring.repository.CategoryRepo;
import com.example.testspring.services.CategoryService;
import com.example.testspring.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/list")
    public ResponseDTO<List<CommentDTO>> list(){
        List<CommentDTO> comments = commentService.readAll();
        return ResponseDTO.<List<CommentDTO>>builder()
                .status(200)
                .msg("OK")
                .data(comments)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newComment(@RequestBody @Valid CommentDTO commentDTO){
        commentService.create(commentDTO);
        return ResponseDTO.<Void>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<CommentDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<CommentDTO>builder()
                .status(200)
                .data(commentService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<CommentDTO> delete(@PathVariable("id") int id){
        commentService.delete(id);
        return ResponseDTO.<CommentDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<CommentDTO> update(@RequestBody CommentDTO commentDTO){
        commentService.update(commentDTO);
        return ResponseDTO.<CommentDTO>builder().status(200).msg("OK")
                .data(commentDTO).build();
    }
}
