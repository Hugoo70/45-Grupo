package com.example.apiExceltic.sergioMostacero.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiExceltic.sergioMostacero.model.Role;
import com.example.apiExceltic.sergioMostacero.model.User;
import com.example.apiExceltic.sergioMostacero.model.Permission;
import com.example.apiExceltic.sergioMostacero.repository.RoleRepository;
import com.example.apiExceltic.sergioMostacero.repository.UserRepository;
import com.example.apiExceltic.sergioMostacero.repository.PermissionRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    UserRepository userRepository;
    PermissionRepository permissionRepository;

    public List<Role> getRoles() {
        List<Role> getallRoles = roleRepository.findAll();
        return getallRoles;
    }


    public Role getRoleById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.get();
    }

    public List<Permission> getPermissionsByRoleId(Long id){
        Optional <Role> optionalRole= roleRepository.findById(id);
        return optionalRole.get().getPermissions();
    }
    

    public Role deleteRoleById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new RuntimeException("ROLE_NOT_FOUND");
        }
        Role role = optionalRole.get();
        role.getPermissions().clear();
        roleRepository.save(role);
        roleRepository.deleteById(id);
        return optionalRole.get();

    }


    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public boolean existsByName(String name){
        return roleRepository.existsByName(name);
    }

    public Role updateName(Long id, String newName) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setName(newName);
            return roleRepository.save(role);
        } else {
            throw new RuntimeException("Role not found");

        }

    }

    public Role deleteRoleAdmin(Long roleId, Long userId) {

        if (userId != 1) {
            throw new RuntimeException("Only the user with ID 1 can delete roles");
        }

        // si el id es 1 borra el role
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {
            roleRepository.deleteById(roleId);
            return optionalRole.get();
        } else {
            throw new RuntimeException("Role not found");
        }
    }

}
