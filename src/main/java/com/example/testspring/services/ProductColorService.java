package com.example.testspring.services;

import com.example.testspring.dto.ProductColorDTO;
import com.example.testspring.dto.ProductDTO;
import com.example.testspring.dto.ProductSizeDTO;
import com.example.testspring.dto.SizeDTO;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.ProductColor;
import com.example.testspring.entity.Size;
import com.example.testspring.repository.ProductColorRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
class ProductColorServiceImpl implements ProductColorService{
    @Autowired
    ProductColorRepo productColorRepo;

    @Override
    public void create(ProductColorDTO productColorDTO) {
        ProductColor size = new ModelMapper().map(productColorDTO,ProductColor.class);
        productColorRepo.save(size);
    }

    @Override
    public void update(ProductColorDTO productColorDTO) {

        ProductColor productColor = productColorRepo.findById(productColorDTO.getId()).orElseThrow(NoResultException::new);
        if (productColor!=null){
//            productColor.setProduct(productColorDTO.getProduct().getProductColors().get);
            productColorRepo.save(productColor);
        }
    }

    private void updateProductColors(Product product,List<ProductColor> productColors) {

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
