package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "empleado")
@Views({
        @View(name = "Simple", members = "nombre, apellido, rol, activo"),
        @View(name = "Completo", members = "nombre, apellido, rol, correo, telefono, activo")
})
public class Empleado extends BaseEntity {

    @Column(length = 50, nullable = false)
    @Required
    @DisplaySize(20)
    private String nombre;

    @Column(length = 50, nullable = false)
    @Required
    @DisplaySize(20)
    private String apellido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required
    private Rol rol;

    @Column(length = 100, unique = true)
    private String correo;

    @Column(length = 20)
    private String telefono;

    private Boolean activo = true;

    @ReadOnly
    @DisplaySize(40)
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}