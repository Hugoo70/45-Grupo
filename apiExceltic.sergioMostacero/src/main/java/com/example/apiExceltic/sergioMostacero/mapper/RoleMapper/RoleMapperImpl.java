package com.example.apiExceltic.sergioMostacero.mapper.RoleMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.example.apiExceltic.sergioMostacero.model.Role;

@Component
public class RoleMapperImpl implements RoleMapper{

    @Override
    public RoleDTO toDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    @Override
    public List<RoleDTO> toListDTO(List<Role> roles){
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (Role role : roles) {
            roleDTOs.add(toDTO(role));
        }
        return roleDTOs;
    }


}
