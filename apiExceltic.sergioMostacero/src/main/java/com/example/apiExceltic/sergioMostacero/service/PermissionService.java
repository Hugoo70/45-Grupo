package com.example.apiExceltic.sergioMostacero.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiExceltic.sergioMostacero.model.Permission;
import com.example.apiExceltic.sergioMostacero.model.Role;
import com.example.apiExceltic.sergioMostacero.repository.PermissionRepository;
import com.example.apiExceltic.sergioMostacero.repository.RoleRepository;

@Service
public class PermissionService {

   @Autowired
   PermissionRepository permissionRepository;
   RoleRepository roleRepository;

   public List<Permission> getPermission() {

      List<Permission> getallPermission = permissionRepository.findAll();
      return getallPermission;
   }

   public Permission getPermissionById(Long id) {
      Optional<Permission> optionalPermission = permissionRepository.findById(id);

      return optionalPermission.get();
   }

   public List<Role> getRolesByPermissionId(Long id) {
      Optional<Permission> optionalPermission = permissionRepository.findById(id);
      return optionalPermission.get().getRoles();
   }

   public Permission deletePermissionById(Long id) {
      Optional<Permission> optionalPermission = permissionRepository.findById(id);
      permissionRepository.deleteById(id);
      return optionalPermission.get();

   }

   public Permission createPermission(Permission permission) {
      return permissionRepository.save(permission);
   }

   public boolean existsByName(String name) {
      return permissionRepository.existsByName(name);
   }

   public Permission updatePermissionName(Long id, String newName) {
      Optional<Permission> optionalPermission = permissionRepository.findById(id);
      if (optionalPermission.isPresent()) {
         Permission permission = optionalPermission.get();
         permission.setName(newName);
         return permissionRepository.save(permission);

      } else {
         throw new RuntimeException("Permission not found");
      }
   }

   public Permission deletePermissionAdmin(Long permissionId, Long roleId) {
      // erificar si el rol tiene permisos para eliminar
      Optional<Role> roleOptional = roleRepository.findById(roleId);
      if (roleOptional.isEmpty() || !roleOptional.get().getName().equalsIgnoreCase("Admin")) {

         throw new RuntimeException("Only Admin roles can delete permissions");
      }

      Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);
      if (permissionOptional.isPresent()) {
         permissionRepository.deleteById(permissionId);
         return permissionOptional.get();
      } else {
         throw new RuntimeException("Permission not found");
      }
   }

}
