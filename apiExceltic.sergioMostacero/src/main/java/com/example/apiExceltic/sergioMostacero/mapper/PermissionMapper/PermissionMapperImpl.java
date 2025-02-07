package com.example.apiExceltic.sergioMostacero.mapper.PermissionMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.apiExceltic.sergioMostacero.dto.PermissionDTO;
import com.example.apiExceltic.sergioMostacero.model.Permission;

@Component
public class PermissionMapperImpl implements PermissionMapper{
    
    @Override
    public PermissionDTO toDTO(Permission permission){
        PermissionDTO permissionDTO = new PermissionDTO();

        permissionDTO.setName(permission.getName());

        return permissionDTO;
    }

    @Override
    public List<PermissionDTO> toListDTO(List<Permission>permissions){
        List<PermissionDTO> permissionDTOs = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionDTOs.add(toDTO(permission));
        }
        return permissionDTOs;
    }


}
