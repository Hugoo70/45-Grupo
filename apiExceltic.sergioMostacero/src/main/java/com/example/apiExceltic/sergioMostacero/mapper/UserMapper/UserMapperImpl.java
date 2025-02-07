package com.example.apiExceltic.sergioMostacero.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.example.apiExceltic.sergioMostacero.dto.UserDTO;
import com.example.apiExceltic.sergioMostacero.model.User;

import jakarta.validation.constraints.Null;

@Component
public class UserMapperImpl implements UserMapper{

    @Override
    public List<UserDTO> toListDTO(List<User> users) {

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {

            userDTOs.add(toDTO(user));
        }

        return userDTOs;
    }

    @Override
    public UserDTO toDTO(User user) {
        //convierte un solo usr a UserDTO
        UserDTO userDTO = new UserDTO();

        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
    
        if(user.getRole() !=null){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(user.getRole().getId());
            roleDTO.setName(user.getRole().getName());
            userDTO.setRoleDTO(roleDTO);
        }

        return userDTO;
    }
    
}
