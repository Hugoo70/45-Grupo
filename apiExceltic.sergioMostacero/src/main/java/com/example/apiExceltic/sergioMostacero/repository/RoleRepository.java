package com.example.apiExceltic.sergioMostacero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apiExceltic.sergioMostacero.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    boolean existsByName(String name);
}
