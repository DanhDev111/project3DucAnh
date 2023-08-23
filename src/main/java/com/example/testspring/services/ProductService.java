package com.example.testspring.services;

import com.example.testspring.dto.*;
import com.example.testspring.entity.*;
import com.example.testspring.repository.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void delete(int id);

    ProductDTO getById(int id);

    List<ProductDTO> readAll();

    PageDTO<ProductDTO> searchName(SearchPriceDTO searchDTO);
}

@Service
class ProductServiceImpl implements ProductService {
    @Autowired
    ProductColorRepo productColorRepo;
    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductRepo productRepo;

    @Override
    public ProductDTO create(ProductDTO productDTO) {

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());
        product.setImage(productDTO.getImage());
        Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);
        product.setCategory(category);
        List<ProductColor> productColors = new ArrayList<>();
        for (ProductColor productColor : productDTO.getProductColors()) {
            productColor.setProduct(product);
            productColors.add(productColor);
        }


        // Lấy thông tin Sizes từ DTO và lưu vào sản phẩm
        List<SizeDTO> sizeDTOs = productDTO.getSizeDTOs();
        List<Size> sizes = sizeDTOs.stream()
                .map(sizeDTO -> sizeRepo.findById(sizeDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Size not found")))
                .collect(Collectors.toList());
        product.setProductColors(productColors);
        product.setSizes(sizes);
        productRepo.save(product);


        return convert(product);
    }

    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        Product product = productRepo.findById(productDTO.getId()).orElseThrow(NoResultException::new);

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());
        product.setImage(productDTO.getImage());

        product.setCategory(categoryRepo.findById(productDTO.getCategory().getId()).orElse(null));


        // Cập nhật thông tin màu sắc
        updateProductColors(product, productDTO.getProductColors());

        // Cập nhật thông tin kích thước
        List<SizeDTO> updatedSizeDTOs = productDTO.getSizeDTOs();
        List<Size> updatedSizes = updatedSizeDTOs.stream()
                .map(sizeDTO -> sizeRepo.findById(sizeDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Size not found")))
                .collect(Collectors.toList());
        product.getSizes().clear();
        product.getSizes().addAll(updatedSizes);
        product.setSizes(updatedSizes);



        // Lưu thay đổi vào cơ sở dữ liệu
        productRepo.save(product);

        return convert(product);
    }

    private void updateProductColors(Product product, List<ProductColor> productColors) {
        for (ProductColor productColor : productColors) {
            productColor.setProduct(product);
        }

        product.getProductColors().clear();
        product.getProductColors().addAll(productColors);
    }

    @Override
    public void delete(int id) {
        productRepo.deleteById(id);
    }

    private ProductDTO convert(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO getById(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product != null) {
            return convert(product);
        }
        return null;
    }

    @Override
    public List<ProductDTO> readAll() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        return products.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<ProductDTO> searchName(SearchPriceDTO searchDTO) {
        Sort sort = Sort.by("name").descending();
        if (StringUtils.hasText(searchDTO.getSortedField())) {
            sort = Sort.by(searchDTO.getSortedField()).descending();
        }
        if (searchDTO.getCurrentPage() == null) {
            searchDTO.setCurrentPage(0);
        }
        if (searchDTO.getSize() == null) {
            searchDTO.setSize(10);
        }
        if (searchDTO.getKeyword() == null) {
            searchDTO.setKeyword("");
        }

        Pageable pageable = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sort);
        Page<Product> page = productRepo.findAll(pageable);
        if (searchDTO.getKeyword() != null) {
            page = productRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageable);
        } else if (searchDTO.getCategoryId() != null) {
            page = productRepo.searchByCategory(searchDTO.getCategoryId(), pageable);
        } else if (searchDTO.getStart() != null && searchDTO.getEnd() != null) {
            page = productRepo.searchByPrice(searchDTO.getStart(), searchDTO.getEnd(), pageable);
        }
        List<ProductDTO> productDTOS = page.get().map(u -> convert(u)).collect(Collectors.toList());
        return PageDTO.<ProductDTO>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .data(productDTOS)
                .build();
    }


}
