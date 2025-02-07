package com.example.apiExceltic.sergioMostacero.mapper.PermissionMapper;

import java.util.List;

import com.example.apiExceltic.sergioMostacero.dto.PermissionDTO;
import com.example.apiExceltic.sergioMostacero.model.Permission;

public interface PermissionMapper {

    List<PermissionDTO> toListDTO(List<Permission>permissions);

    PermissionDTO toDTO(Permission permission);
}
