package com.example.apiExceltic.sergioMostacero.mapper.UserMapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.apiExceltic.sergioMostacero.dto.UserDTO;
import com.example.apiExceltic.sergioMostacero.model.User;

public interface UserMapper {
    List<UserDTO> toListDTO(List<User> users); //convierte lista users a usersDTO

    UserDTO toDTO(User userDB); //convierte un user a UserDTO
}