package com.example.testspring.services;


import com.example.testspring.dto.SizeDTO;
import com.example.testspring.entity.Size;
import com.example.testspring.repository.SizeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface SizeService {
    void create(SizeDTO sizeDTO);

    void update(SizeDTO sizeDTO);

    void delete(int id);

    SizeDTO getById(int id);

    List<SizeDTO> readAll();
}
@Service
class SizeServiceImpl implements SizeService{
    @Autowired
    SizeRepo sizeRepo;

    @Override
    public void create(SizeDTO sizeDTO) {
        Size size = new ModelMapper().map(sizeDTO,Size.class);
        sizeRepo.save(size);
    }

    @Override
    public void update(SizeDTO sizeDTO) {
        Size size = sizeRepo.findById(sizeDTO.getId()).orElseThrow(NoResultException::new);
        if (size!=null){
            size.setName(sizeDTO.getName());
        sizeRepo.save(size);
        }
    }

    @Override
    public void delete(int id) {
        sizeRepo.deleteById(id);
    }

    @Override
    public SizeDTO getById(int id) {
        Size size = sizeRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(size);
    }

    public SizeDTO convert(Size size){
        return new ModelMapper().map(size,SizeDTO.class);
    }
    @Override
    public List<SizeDTO> readAll() {
        List<Size> sizes = sizeRepo.findAll();
        List<SizeDTO> sizeDTOS = new ArrayList<>();
        return sizes.stream().map(u ->convert(u)).collect(Collectors.toList());
    }
}
