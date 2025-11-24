package org.example.DataLogic.model;

public class Rol extends BaseEntity {
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
