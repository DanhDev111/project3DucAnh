package com.example.testspring.services;

import com.example.testspring.dto.ProductColorDTO;
import com.example.testspring.entity.Color;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.ProductColor;
import com.example.testspring.repository.ColorRepo;
import com.example.testspring.repository.ProductColorRepo;
import com.example.testspring.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ProductColorService {
    void create(ProductColorDTO productColorDTO);

    void update(ProductColorDTO productColorDTO);

    void delete(int id);

    ProductColorDTO getById(int id);

    List<ProductColorDTO> readAll();
}
@Service
class ProductColorServiceImpl implements ProductColorService{
    @Autowired
    ProductColorRepo productColorRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ColorRepo colorRepo;

    @Override
    public void create(ProductColorDTO productColorDTO) {
        ProductColor productColor = productColorRepo.findById(productColorDTO.getId()).orElseThrow(NoResultException::new);
        Product product = productRepo.findById(productColorDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        Color color = colorRepo.findById(productColorDTO.getColor().getId()).orElseThrow(NoResultException::new);
        productColor.setProduct(product);
        productColor.setColor(color);
        productColorRepo.save(productColor);
    }

    @Override
    public void update(ProductColorDTO productColorDTO) {

        ProductColor productColor = productColorRepo.findById(productColorDTO.getId()).orElseThrow(NoResultException::new);
        Product product = productRepo.findById(productColorDTO.getProduct().getId()).orElseThrow(NoResultException::new);
        Color color = colorRepo.findById(productColorDTO.getColor().getId()).orElseThrow(NoResultException::new);
        productColor.setProduct(product);
        productColor.setColor(color);
        productColor.setQuantity(productColorDTO.getQuantity());


        List<String> newImages = new ArrayList<>();
        if (productColor != null) {

            productColor.getImages().clear();
            productColor.getImages().addAll(newImages);
//            productColor.setProduct(product);
//            productColor.setColor(color);
        }
        productColorRepo.save(productColor);



    }

    private void updateProductColors(ProductColor product,List<String> images) {

    }
    @Override
    public void delete(int id) {
        productColorRepo.deleteById(id);
    }

    @Override
    public ProductColorDTO getById(int id) {
        ProductColor productColor = productColorRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(productColor);
    }

    public ProductColorDTO convert(ProductColor size){
        return new ModelMapper().map(size,ProductColorDTO.class);
    }
    @Override
    public List<ProductColorDTO> readAll() {
        List<ProductColor> sizes = productColorRepo.findAll();
        List<ProductColorDTO> productSizeDTOs = new ArrayList<>();
        return sizes.stream().map(u ->convert(u)).collect(Collectors.toList());
    }

}
