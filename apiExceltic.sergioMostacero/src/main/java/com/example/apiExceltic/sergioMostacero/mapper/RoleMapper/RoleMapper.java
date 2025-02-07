package com.example.apiExceltic.sergioMostacero.mapper.RoleMapper;

import java.util.List;

import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.example.apiExceltic.sergioMostacero.model.Role;

public interface RoleMapper {
    List<RoleDTO> toListDTO(List<Role>roles);

    RoleDTO toDTO(Role roleDB);
}
