package com.example.apiExceltic.sergioMostacero.model;

import java.util.ArrayList;

import com.example.apiExceltic.sergioMostacero.Validation.CorrectNumber;
import com.example.apiExceltic.sergioMostacero.Validation.EmailConstraint;
import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "User name is mandatory")
    private String name;
    
    @NotBlank(message = "User mail is mandatory")
    @EmailConstraint(message = "Invalid email format. Must be in the format example@domain.com")
    private String email;
    
    @NotBlank(message = "User password is mandatory")
    private String password;
    
    @NotBlank(message = "Phone number is mandatory")
    @CorrectNumber(message = "phone number out of format")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id_fk")
    private Role role;

}
