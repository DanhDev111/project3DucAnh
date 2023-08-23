package com.example.testspring.controller;

import com.example.testspring.dto.ReviewDTO;
import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.services.CommentService;
import com.example.testspring.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @PostMapping("/list")
    public ResponseDTO<List<ReviewDTO>> list(){
        List<ReviewDTO> reviews = reviewService.readAll();
        return ResponseDTO.<List<ReviewDTO>>builder()
                .status(200)
                .msg("OK")
                .data(reviews)
                .build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newReview(@RequestBody @Valid ReviewDTO reviewDTO){
        reviewService.create(reviewDTO);
        return ResponseDTO.<Void>builder().status(200)
                .msg("OK")
                .build();
    }
    @GetMapping("/")
    public ResponseDTO<ReviewDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<ReviewDTO>builder()
                .status(200)
                .data(reviewService.getById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<ReviewDTO> delete(@PathVariable("id") int id){
        reviewService.delete(id);
        return ResponseDTO.<ReviewDTO>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO){
        reviewService.update(reviewDTO);
        return ResponseDTO.<ReviewDTO>builder().status(200).msg("OK")
                .data(reviewDTO).build();
    }
}
