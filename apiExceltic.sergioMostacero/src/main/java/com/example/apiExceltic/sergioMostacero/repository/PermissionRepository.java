package com.example.apiExceltic.sergioMostacero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apiExceltic.sergioMostacero.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
    boolean existsByName(String name);
}
