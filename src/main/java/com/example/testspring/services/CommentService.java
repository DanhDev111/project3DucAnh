package com.example.testspring.services;

import com.example.testspring.dto.CommentDTO;
import com.example.testspring.dto.PageDTO;
import com.example.testspring.dto.ReviewDTO;
import com.example.testspring.entity.Comment;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.User;
import com.example.testspring.repository.CommentRepo;
import com.example.testspring.repository.ProductRepo;
import com.example.testspring.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;
    public void create(CommentDTO commentDTO){
        Comment comment = new Comment();
        Product product = productRepo.findById(commentDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        User user = userRepo.findById(commentDTO.getUser().getId()).orElseThrow(NoResultException::new);
        comment.setProduct(product);
        comment.setUser(user);

        comment.setComment(commentDTO.getComment());
        commentRepo.save(comment);
    }
    public void update(CommentDTO commentDTO){
        Comment comment = commentRepo.findById(commentDTO.getId()).orElseThrow(NoResultException::new);
        Product product = productRepo.findById(commentDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        comment.setComment(commentDTO.getComment());
        comment.setProduct(product);
        comment.setUser(userRepo.findById(commentDTO.getUser().getId()).orElseThrow(NoResultException::new));
        commentRepo.save(comment);
    }
    public void delete(int id){
        commentRepo.deleteById(id);
    }
    public CommentDTO convert(Comment comment){
        return new ModelMapper().map(comment,CommentDTO.class);
    }
    public CommentDTO getById(int id){

        Comment comment = commentRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(comment);
    }
    public List<CommentDTO> readAll(){
        List<Comment> commentList = commentRepo.findAll();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        return commentList.stream().map(c ->convert(c)).collect(Collectors.toList());
    }
}
