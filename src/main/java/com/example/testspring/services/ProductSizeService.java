package com.example.testspring.services;

import com.example.testspring.dto.ProductColorDTO;
import com.example.testspring.dto.ProductSizeDTO;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.ProductColor;
import com.example.testspring.entity.ProductSize;
import com.example.testspring.repository.ProductColorRepo;
import com.example.testspring.repository.ProductRepo;
import com.example.testspring.repository.ProductSizeRepo;
import com.example.testspring.repository.SizeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProductSizeService {
    void create(ProductSizeDTO productSizeDTO);

    ProductSizeDTO update(ProductSizeDTO productSizeDTO);

    void delete(int id);

    ProductSizeDTO getById(int id);

    List<ProductSizeDTO> readAll();
}

@Service
class ProductSizeServiceImpl implements ProductSizeService {
    @Autowired
    ProductSizeRepo productSizeRepo;

    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    ProductRepo productRepo;

    @Override
    @Transactional
    public void create(ProductSizeDTO productSizeDTO) {
        ProductSize size = new ModelMapper().map(productSizeDTO, ProductSize.class);
        productSizeRepo.save(size);
    }

    @Override
    @Transactional
    public ProductSizeDTO update(ProductSizeDTO productSizeDTO) {
        ProductSize productSize = productSizeRepo.findById(productSizeDTO.getId()).orElseThrow(NoResultException::new);
        // Kiểm tra xem productSizeDTO có id không để xác định cập nhật hay thêm mới
//        if (productSizeDTO.getId() != null) {
//            Optional<ProductSize> existingProductSizeOptional = productSizeRepo.findById(productSizeDTO.getId());
//            if (existingProductSizeOptional.isPresent()) {
//                ProductSize existingProductSize = existingProductSizeOptional.get();
//                existingProductSize.setSize(productSizeDTO.getSize());
////                existingProductSize.setProduct(productSizeDTO.getProduct());
//            }
//        }

        productSize.setSize(sizeRepo.findById(productSizeDTO.getSize().getId()).orElseThrow(NoResultException::new));


//        List<ProductSize> productSizes = new ArrayList<>();
//        for (Size newSize : productSizeDTO.getSize()) {
//            for (ProductSize existingSize : productSizes) {
//                if (existingSize.getId().equals(newSize.getId())) {
//                    // Cập nhật thông tin cho kích thước đã tồn tại
//                    existingSize.setSize(newSize);
//                    // Các cập nhật khác...
//                } else if (newSize.getId() == null) {
//                    productSizes.add(existingSize);
//                }
//            }
//        }
        productSize.setProduct(productRepo.findById(productSizeDTO.getProduct().getId()).orElseThrow(NoResultException::new));

        productSizeRepo.save(productSize);
        return convert(productSize);
    }

    private void updateProductSizes(ProductSizeDTO productSizeDTO,List<ProductSize> productSizes) {


    }

    @Override
    @Transactional
    public void delete(int id) {
        productSizeRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ProductSizeDTO getById(int id) {
        ProductSize productSize = productSizeRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(productSize);
    }

    public ProductSizeDTO convert(ProductSize productSize) {

        return new ModelMapper().map(productSize, ProductSizeDTO.class);
    }

    @Override
    public List<ProductSizeDTO> readAll() {
        List<ProductSize> sizes = productSizeRepo.findAll();

        List<ProductSizeDTO> productSizeDTOs = new ArrayList<>();
        return sizes.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

}
