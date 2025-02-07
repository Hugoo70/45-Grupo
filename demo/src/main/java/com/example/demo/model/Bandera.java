package com.example.demo.model;

import java.util.ArrayList;
import com.example.apiExceltic.sergioMostacero.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "bandera")
public class Bandera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "imagen obligatorio")
    private String imagen;



    @OneToMany(mappedBy = "bandera", cascade = CascadeType.ALL) 
    private List<Vuelo> vuelos = new ArrayList<>();

}
