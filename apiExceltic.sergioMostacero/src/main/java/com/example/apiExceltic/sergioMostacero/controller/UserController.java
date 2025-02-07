package com.example.apiExceltic.sergioMostacero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiExceltic.sergioMostacero.dto.PermissionDTO;
import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.example.apiExceltic.sergioMostacero.dto.UserDTO;
import com.example.apiExceltic.sergioMostacero.dto.RoleAssignmentDTO;
import com.example.apiExceltic.sergioMostacero.mapper.PermissionMapper.PermissionMapper;
import com.example.apiExceltic.sergioMostacero.mapper.RoleMapper.RoleMapper;
import com.example.apiExceltic.sergioMostacero.mapper.UserMapper.UserMapper;
import com.example.apiExceltic.sergioMostacero.model.Permission;
import com.example.apiExceltic.sergioMostacero.model.User;
import com.example.apiExceltic.sergioMostacero.service.UserService;
import com.example.apiExceltic.sergioMostacero.service.RoleService;
import com.example.apiExceltic.sergioMostacero.service.PermissionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<User> users = userService.getUsers(); //Obtiene usrs desd el servicio
        List<UserDTO> userDTOs = userMapper.toListDTO(users);//convierte ususarios a DTOs

        if (!users.isEmpty()) {
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("EMPTY_DATABASE", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User userDB = userService.getUserById(id);
        if (userDB != null) {
            UserDTO userDTO = userMapper.toDTO(userDB);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT-FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        User userDB = userService.getUserByPhone(phoneNumber);
        if (userDB != null) {
            UserDTO userDTO = userMapper.toDTO(userDB); 
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping("permissions/{id}")
    public ResponseEntity<?> getPermissionsByUserId(@PathVariable Long id) {
        try {
            List<Permission> permissions = userService.getPermissionByUserId(id);
            List<PermissionDTO>permissionDTOs= permissionMapper.toListDTO(permissions);
            return new ResponseEntity<>(permissionDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<?> getUserRoles(@PathVariable Long id){
        User userdb = userService.getUserById(id);
        if(userdb !=null){
            RoleDTO roleDTO =roleMapper.toDTO(userdb.getRole());
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        User userDB = userService.getUserById(id);
        UserDTO userDTO= userMapper.toDTO(userDB); // se pasa el usuario interno a DTO para luego mostrar el usuario borrado
        if (userDB != null) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) { // BindingResult allows
                                                                                               // the validator to be
                                                                                               // applied
        try {
            if (userService.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("USER_ALREADY_EXISTS", HttpStatus.NOT_FOUND);
            }
            User createdUser = userService.createUser(user);
            UserDTO userDTO= userMapper.toDTO(createdUser);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{idUser}/role")
    public ResponseEntity<?> addRol(@PathVariable Long idUser, @RequestBody RoleAssignmentDTO roleAssignmentDTO) {

        User userDB = userService.getUserById(idUser);
        if (userDB != null) {
            userService.newRolUser(idUser, roleAssignmentDTO);
            User updatedUser = userService.getUserById(idUser);
            UserDTO userDTO= userMapper.toDTO(updatedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        User userDB = userService.getUserById(id);
        if (userDB != null) {
            userService.deleteRole(id);
            User updatedUser= userService.getUserById(id);
            UserDTO userDTO= userMapper.toDTO(updatedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        User userDB = userService.getUserById(id);
        if (userDB != null) {
            User updatedUser = userService.updatePassword(id, newPassword);
            UserDTO userDTO = userMapper.toDTO(updatedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

}
