package com.example.testspring.services;

import com.example.testspring.dto.CategoryDTO;
import com.example.testspring.dto.PageDTO;
import com.example.testspring.dto.RoleDTO;
import com.example.testspring.dto.SearchDTO;
import com.example.testspring.entity.Category;
import com.example.testspring.entity.User;
import com.example.testspring.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoryService {
    void create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int id);

    CategoryDTO getById(int id);

    List<CategoryDTO> readAll();

    PageDTO<CategoryDTO> searchByName(SearchDTO searchDTO);
}
@Service
class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    @Transactional
    public void create(CategoryDTO categoryDTO) {
        Category category = new ModelMapper().map(categoryDTO, Category.class);
        categoryRepo.save(category);
    }

    @Override
    @Transactional
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);
        if (category!=null){
            category.setName(category.getName());
        }
        categoryRepo.save(category);
    }

    @Override
    @Transactional
    public void delete(int id) {
        categoryRepo.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDTO getById(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
        if (category != null) {
            return convert(category);
        }
        return null;
    }

    @Override
    @Transactional
    public List<CategoryDTO> readAll() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        return categories.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

    public CategoryDTO convert(Category category) {
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    @Override
    public PageDTO<CategoryDTO> searchByName(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("name").ascending();

//        if (sortBy !=null && !sortBy.isEmpty()){
//
//        }
        if (StringUtils.hasText(searchDTO.getSortedField())){
            sortBy=  Sort.by(searchDTO.getSortedField()).descending();
        }
        if (searchDTO.getCurrentPage()==null){
            searchDTO.setCurrentPage(0);
        }
        if (searchDTO.getSize()==null){
            searchDTO.setSize(5);
        }
        if (searchDTO.getKeyword()==null){
            searchDTO.setKeyword("");
        }

        Pageable pageable =
                PageRequest.of(searchDTO.getCurrentPage(),searchDTO.getSize(),sortBy);
        Page<Category> page = categoryRepo.searchByName("%"+searchDTO.getKeyword()+"%", pageable);

        List<CategoryDTO> categoryDTOS = page.get().map(u ->convert(u)).collect(Collectors.toList());

        return PageDTO.<CategoryDTO>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .data(categoryDTOS)
                .build();
    }
}
