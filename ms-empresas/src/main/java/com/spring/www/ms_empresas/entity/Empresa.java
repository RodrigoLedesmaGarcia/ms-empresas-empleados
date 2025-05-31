package com.spring.www.ms_empresas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nombre de la empresa no puede estar vacio")
    private String nombre;

    @NotBlank(message = "nombre de la industria no puede estar vacio")
    private String industria;

    public Empresa() {
    }

    public Empresa(Long id, String nombre, String industria) {
        this.id = id;
        this.nombre = nombre;
        this.industria = industria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
    }
}
