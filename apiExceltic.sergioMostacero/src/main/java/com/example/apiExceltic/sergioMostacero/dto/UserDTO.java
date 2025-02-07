package com.example.apiExceltic.sergioMostacero.dto;

import com.example.apiExceltic.sergioMostacero.Validation.CorrectNumber;
import com.example.apiExceltic.sergioMostacero.Validation.EmailConstraint;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "User name is mandatory")
    private String name;
    
    @NotBlank(message = "User mail is mandatory")
    @EmailConstraint(message = "Invalid email format. Must be in the format example@domain.com")
    private String email;
    
    @NotBlank(message = "Phone number is mandatory")
    @CorrectNumber(message = "phone number out of format")
    private String phoneNumber;
    
    RoleDTO roleDTO;


}
