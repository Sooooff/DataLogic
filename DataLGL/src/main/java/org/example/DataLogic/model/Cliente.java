package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "cliente")
public class Cliente extends BaseEntity {

    @Column(length = 50, nullable = false)
    @Required
    @DisplaySize(20)
    private String nombre;

    @Column(length = 50, nullable = false)
    @Required
    @DisplaySize(20)
    private String apellido;

    @Column(length = 100, unique = true)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    @TextArea
    private String direccion;

    @ReadOnly
    @DisplaySize(40)
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}