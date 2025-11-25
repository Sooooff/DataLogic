package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Rol extends BaseEntity {
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Rol() {

    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
