package com.example.apiExceltic.sergioMostacero.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiExceltic.sergioMostacero.model.User;
import com.example.apiExceltic.sergioMostacero.model.Role;
import com.example.apiExceltic.sergioMostacero.dto.RoleAssignmentDTO;
import com.example.apiExceltic.sergioMostacero.dto.UserDTO;
import com.example.apiExceltic.sergioMostacero.model.Permission;

import com.example.apiExceltic.sergioMostacero.repository.UserRepository;
import com.example.apiExceltic.sergioMostacero.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    public List<User> getUsers() {

        try {
            List<User> getAllUsers = userRepository.findAll();
            return getAllUsers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            return optionalUser.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Role getRoleById(Long id) {
        try {
            Optional<Role> optionalRole = roleRepository.findById(id);
            if (optionalRole.isPresent()) {
                return optionalRole.get();
            }
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public User getUserByPhone(String phoneNumber) {
        try {
            Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
            return optionalUser.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Permission> getPermissionByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("USER_NOT_FOUND");
        }
        User user = optionalUser.get();
        Role userRole = user.getRole();

        return userRole.getPermissions();
    }

    public User deleteUserById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return optionalUser.get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); // comprueba si existe el mail ya
    }

    

    public User newRolUser(Long id, RoleAssignmentDTO roleAssignmentDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Long roleId=roleAssignmentDTO.getNewRol();
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        
        if (optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();
            user.setRole(role);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("USER_NOT_FOUND");
        }
    }

    public User deleteRole(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(null);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("USER_NOT_FOUND");
        }
    }

    public User updatePassword(Long id, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("USER_NOT_FOUND");
        }
    }

    
}
