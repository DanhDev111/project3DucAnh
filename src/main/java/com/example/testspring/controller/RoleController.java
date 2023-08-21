package com.example.testspring.controller;

import com.example.testspring.dto.PageDTO;
import com.example.testspring.dto.ResponseDTO;
import com.example.testspring.dto.RoleDTO;
import com.example.testspring.dto.SearchDTO;
import com.example.testspring.entity.Role;
import com.example.testspring.repository.RoleRepo;
import com.example.testspring.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    RoleService roleService;

    @PostMapping("/search")
    public ResponseDTO<PageDTO<RoleDTO>> search(@RequestBody SearchDTO searchDTO){
        PageDTO<RoleDTO> pageDTO = roleService.search(searchDTO);
        return ResponseDTO.<PageDTO<RoleDTO>>builder().status(200).msg("OK").data(pageDTO).build();
    }
    @PostMapping("/")
    public ResponseDTO<Void> newRole(@RequestBody @Valid RoleDTO roleDTO){
        roleService.create(roleDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping("/{id}")// role/1
    public ResponseDTO<Void> delete(@PathVariable("id") int id){
        roleService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }
    @PutMapping("/")
    public ResponseDTO<RoleDTO> update(@RequestBody @Valid RoleDTO roleDTO){
        roleService.update(roleDTO);
        return ResponseDTO.<RoleDTO>builder().status(200).msg("OK").data(roleDTO).build();
    }
    @GetMapping("/")
    public ResponseDTO<RoleDTO> getById(@RequestParam("id") int id){
        return ResponseDTO.<RoleDTO>builder().status(200).msg("OK").data(roleService.getById(id)).build();
    }
}
