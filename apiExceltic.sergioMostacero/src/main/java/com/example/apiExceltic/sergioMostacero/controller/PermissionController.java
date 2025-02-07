package com.example.apiExceltic.sergioMostacero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiExceltic.sergioMostacero.dto.PermissionDTO;
import com.example.apiExceltic.sergioMostacero.mapper.PermissionMapper.PermissionMapper;
import com.example.apiExceltic.sergioMostacero.model.Permission;
import com.example.apiExceltic.sergioMostacero.model.Role;
import com.example.apiExceltic.sergioMostacero.service.PermissionService;
import com.example.apiExceltic.sergioMostacero.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {

    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionMapper permissionMapper;

    @GetMapping
    public ResponseEntity<?> getallpermissions() {
        List<Permission> permissions = permissionService.getPermission();
        List<PermissionDTO>permissionDTOs= permissionMapper.toListDTO(permissions);
        if (!permissions.isEmpty()) {
            return new ResponseEntity<>(permissionDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSIONS_NOT_FOUND", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        Permission permissionDB = permissionService.getPermissionById(id);
        if (permissionDB != null) {
            return new ResponseEntity<>(permissionDB, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSIONS_NOT_FOUND", HttpStatus.NOT_FOUND);
    }


    @GetMapping("permissionByRol/{idPermission}") // ver la lista de roles que tienen ese permiso
    public ResponseEntity<?> getPermissionByRolId(@PathVariable Long idPermission) {
        Permission permissionDB = permissionService.getPermissionById(idPermission);
        if (permissionDB != null) {
            List<Role> role = permissionService.getRolesByPermissionId(idPermission);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Permission permissionDB = permissionService.getPermissionById(id);
        if (permissionDB != null) {
            Permission permission = permissionService.deletePermissionById(id);
            return new ResponseEntity<>(permission, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody Permission permission) {
        if (permissionService.existsByName(permission.getName())) {
            return new ResponseEntity<>("PERMISSION_ALREADY_EXISTS", HttpStatus.NOT_FOUND);
        }
        Permission createpermission = permissionService.createPermission(permission);
        return new ResponseEntity<>(createpermission, HttpStatus.OK);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody String newName) {
        Permission permissionDB = permissionService.getPermissionById(id);
        if (permissionDB != null) {
            Permission permission = permissionService.updatePermissionName(id, newName);
            return new ResponseEntity<>(permission, HttpStatus.OK);
        }
        return new ResponseEntity<>("PERMISSION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("deletepermission/{permissionId}")
    public ResponseEntity<?> deletePermissionAdmin(@PathVariable Long permissionId, @RequestParam Long roleId) {
        Permission deletedPermission = permissionService.deletePermissionAdmin(permissionId, roleId);
        return new ResponseEntity<>(deletedPermission, HttpStatus.OK);
    }

}
