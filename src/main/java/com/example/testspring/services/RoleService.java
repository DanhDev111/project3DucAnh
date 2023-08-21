package com.example.testspring.services;

import com.example.testspring.dto.PageDTO;
import com.example.testspring.dto.RoleDTO;
import com.example.testspring.dto.SearchDTO;
import com.example.testspring.dto.UserDTO;
import com.example.testspring.entity.Role;
import com.example.testspring.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface RoleService {
    void create(RoleDTO roleDTO);
    void update(RoleDTO roleDTO);
    void delete(int id);

    RoleDTO getById(int id);
    List<RoleDTO> readAll();
    PageDTO<RoleDTO> search(SearchDTO searchDTO);

}
@Service
class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    RoleService roleService;

    @Override
    @Transactional
    public void create(RoleDTO roleDTO) {
        Role role = new ModelMapper().map(roleDTO,Role.class);
        roleRepo.save(role);
    }

    @Override
    @Transactional
    public void update(RoleDTO roleDTO) {
        Role role = roleRepo.findById(roleDTO.getId()).orElseThrow(NoResultException::new);
        if (role!=null){
            role = new ModelMapper().map(roleDTO,Role.class);
        }
        roleRepo.save(role);
    }

    @Override
    @Transactional
    public void delete(int id) {
       roleRepo.deleteById(id);
    }

    @Override
    @Transactional
    public RoleDTO getById(int id) {
        //Chú ý nếu như mình muốn xóa 1 list id thì có thể đặt list id
        Role role = roleRepo.findById(id).orElseThrow(NoResultException::new);
        if (role !=null){
            return convert(role);
        }
        return null;
    }

    public RoleDTO convert(Role role){
        return new ModelMapper().map(role,RoleDTO.class);
    }

    @Override
    public List<RoleDTO> readAll() {
        List<Role> roles = roleRepo.findAll();
        List<RoleDTO> roleDTOList = new ArrayList<>();

        return roles.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<RoleDTO> search(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("name").ascending();
        if (StringUtils.hasText(searchDTO.getSortedField())){
            sortBy = Sort.by(searchDTO.getSortedField()).descending();
        }
        if (searchDTO.getCurrentPage()==null){
            searchDTO.setCurrentPage(0);
        }

        if (searchDTO.getKeyword().equals(null)){
            searchDTO.setKeyword("");
        }
        if (searchDTO.getSize()==null){
            searchDTO.setSize(5);
        }
        Pageable pageable = PageRequest.of(searchDTO.getCurrentPage(),searchDTO.getSize(),sortBy);

        Page<Role> pageRS = roleRepo.searchByName("%"+searchDTO.getKeyword()+"%",pageable);

        return PageDTO.<RoleDTO>builder()
                .totalPages(pageRS.getTotalPages())
                .totalElements(pageRS.getTotalElements())
                .data(pageRS.get()
                        .map(r -> convert(r)).collect(Collectors.toList()))
                .build();
    }
}
