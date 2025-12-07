package org.example.DataLogic.model;

import org.openxava.annotations.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Producto {

    @Id @GeneratedValue
    private Long id;

    @Column(length=100)
    private String nombre;

    @Editor(value="org.example.DataLogic.controls.MonedaControl")
    private BigDecimal precio;

    @Editor(value="org.example.DataLogic.controls.MonedaControl")
    private BigDecimal costo;

    // AGREGAR ESTE NUEVO CAMPO
    @Column(precision=10, scale=2)
    private BigDecimal precioUnitario;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    // AGREGAR ESTE GETTER Y SETTER
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}