package com.example.demo.model;

import java.util.ArrayList;

import com.example.demo.validation.CorrectNumber;
import com.example.demo.validation.EmailConstraint;
import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.CascadeType;
import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Nombre de usuario obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido de usuario obligatorio")
    private String apellido;

    @NotBlank(message = "Apellido2 de usuario obligatorio")
    private String apellido2;

    @NotBlank(message = "Grupo sanguineo obligatorio")
    private String grupo_sanguineo;

    @NotBlank(message = "Antigüedad de usuario obligatorio")
    private String antiguedad;

    @NotBlank(message = "Horas de vuelo obligatorio")
    private String horas_vuelo;

    @NotBlank(message = "User mail is mandatory")
    @EmailConstraint(message = "Invalid email format. Must be in the format example@domain.com")
    private String email;
    
    @NotBlank(message = "User password is mandatory")
    private String password;
    
    @NotBlank(message = "Phone number is mandatory")
    @CorrectNumber(message = "phone number out of format")
    private String phoneNumber;



    @ManyToMany
    @JoinTable(name = "usuario_vuelo", joinColumns = @JoinColumn(name = "usuario_id_fk"),
        inverseJoinColumns = @JoinColumn(name = "vuelo_id_fk")
    )
    @JsonIgnore 
    private List<Vuelo> vuelos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rango_id")
    private Rango rango;

    @ManyToOne
    @JoinColumn(name = "destino_id")

}
