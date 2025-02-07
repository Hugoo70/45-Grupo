package com.example.apiExceltic.sergioMostacero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiExceltic.sergioMostacero.dto.PermissionDTO;
import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.example.apiExceltic.sergioMostacero.mapper.RoleMapper.RoleMapper;
import com.example.apiExceltic.sergioMostacero.mapper.PermissionMapper.PermissionMapper;
import com.example.apiExceltic.sergioMostacero.model.Permission;
import com.example.apiExceltic.sergioMostacero.model.Role;
import com.example.apiExceltic.sergioMostacero.service.RoleService;
import com.example.apiExceltic.sergioMostacero.service.PermissionService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController // define clase como REST
@CrossOrigin
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @GetMapping
    public ResponseEntity<?> getallRoles() {
        
        List<Role> roles = roleService.getRoles();
        List<RoleDTO> roleDTOs=roleMapper.toListDTO(roles);
        if (!roles.isEmpty()) {
            
            return new ResponseEntity<>(roleDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLES_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        Role roleBD = roleService.getRoleById(id);
        if (roleBD != null) {
            RoleDTO roleDTO= roleMapper.toDTO(roleBD);
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/roleByPermission/{idRole}") // ver los permisos que tiene ese rol
    public ResponseEntity<?> getPermissionsByID(@PathVariable Long idRole) {
        Role roleDB = roleService.getRoleById(idRole);
        if (roleDB != null) {
            List<Permission> permision = roleService.getPermissionsByRoleId(idRole);
            List<PermissionDTO>permissionDTOs= permissionMapper.toListDTO(permision);
            return new ResponseEntity<>(permissionDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    // roleService.deleteRoleById(id) y devuelve el rol eliminado
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Long id) {
        Role roleDB = roleService.getRoleById(id);
        RoleDTO roleDTO = roleMapper.toDTO(roleDB);
        if (roleDB != null) {
            roleService.deleteRoleById(id);
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

    // roleService.createRole(role) y devuelve el rol recién creado
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {

        if (roleService.existsByName(role.getName())) {
            return new ResponseEntity<>("ROLE_ALREADY_EXISTS", HttpStatus.NOT_FOUND);
        }
        Role createRole = roleService.createRole(role);
        RoleDTO roleDTO= roleMapper.toDTO(createRole);
        return new ResponseEntity<>(roleDTO, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/name")
    // RequestBody para poner en el cuerpo el nombre que quieres introducir
    public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody String newName) {
        Role roleDB = roleService.getRoleById(id);
        if (roleDB != null) {
            roleService.updateName(id, newName);
            RoleDTO roleDTO =roleMapper.toDTO(roleDB);
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("admin/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId, @RequestParam Long userId) {
        Role roleDB = roleService.getRoleById(userId);
        if (roleDB != null) {
            Role deletedRole = roleService.deleteRoleAdmin(roleId, userId);
            RoleDTO roleDTO = roleMapper.toDTO(deletedRole);
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

}
