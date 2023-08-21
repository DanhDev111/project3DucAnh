package com.example.testspring.services;

import com.example.testspring.dto.ColorDTO;

import com.example.testspring.entity.Color;
import com.example.testspring.entity.Size;
import com.example.testspring.repository.ColorRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ColorService {
    void create(ColorDTO colorDTO);

    void update(ColorDTO colorDTO);

    void delete(int id);

    ColorDTO getById(int id);

    List<ColorDTO> readAll();
}
@Service
class ColorServiceImpl implements ColorService{

    @Autowired
    ColorRepo colorRepo;
    @Override
    @Transactional
    @CacheEvict (cacheNames = "color-new",allEntries = true)
    public void create(ColorDTO colorDTO) {
        Color color = new ModelMapper().map(colorDTO,Color.class);
        colorRepo.save(color);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict (cacheNames = "color-search",allEntries = true)
            },
            put = {
                    @CachePut(cacheNames = "color",key = "#colorDTO.id")
            }
    )
    @CachePut(cacheNames = "color",key = "#colorDTO.id")
    public void update(ColorDTO colorDTO) {
        Color color = colorRepo.findById(colorDTO.getId()).orElseThrow(NoResultException::new);
        if (color!=null){
            color.setName(colorDTO.getName());
            colorRepo.save(color);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "color",key = "#id"),
            @CacheEvict(cacheNames = "color2",allEntries = true)
    })
    public void delete(int id) {
        colorRepo.deleteById(id);
    }

    public ColorDTO convert(Color color){
        return new ModelMapper().map(color,ColorDTO.class);
    }
    @Override
    @Cacheable(cacheNames = "color",key = "#id",unless = "#result == null ")
    public ColorDTO getById(int id) {
        Color color = colorRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(color);
    }

    @Override
    public List<ColorDTO> readAll() {
        List<Color> colors = colorRepo.findAll();
        List<ColorDTO> colorDTOS = new ArrayList<>();
        return colors.stream().map(u ->convert(u)).collect(Collectors.toList());
    }
}
