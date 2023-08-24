package com.example.testspring.services;

import com.example.testspring.dto.ReviewDTO;
import com.example.testspring.entity.Comment;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.Review;
import com.example.testspring.entity.User;
import com.example.testspring.repository.CommentRepo;
import com.example.testspring.repository.ProductRepo;
import com.example.testspring.repository.ReviewRepo;
import com.example.testspring.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;
    public void create(ReviewDTO reviewDTO){
        Review review = new Review();
        Product product = productRepo.findById(reviewDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        User user = userRepo.findById(reviewDTO.getUser().getId()).orElseThrow(NoResultException::new);
        review.setProduct(product);
        review.setUser(user);

        review.setStarNumber(reviewDTO.getStarNumber());
        reviewRepo.save(review);
    }
    public void update(ReviewDTO reviewDTO){
        Review review = reviewRepo.findById(reviewDTO.getId()).orElseThrow(NoResultException::new);
        Product product = productRepo.findById(reviewDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        review.setStarNumber(reviewDTO.getStarNumber());
        review.setProduct(product);
        review.setUser(userRepo.findById(reviewDTO.getUser().getId()).orElseThrow(NoResultException::new));
        reviewRepo.save(review);
    }
    public void delete(int id){
        reviewRepo.deleteById(id);
    }
    public ReviewDTO convert(Review review){
        return new ModelMapper().map(review,ReviewDTO.class);
    }
    public ReviewDTO getById(int id){

        Review review = reviewRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(review);
    }
    public List<ReviewDTO> readAll(){
        List<Review> reviewList = reviewRepo.findAll();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        return reviewList.stream().map(c ->convert(c)).collect(Collectors.toList());
    }
}
