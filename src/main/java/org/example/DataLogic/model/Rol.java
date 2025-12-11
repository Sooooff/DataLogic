package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "rol")
public class Rol extends BaseEntity {

    @Column(length = 50, nullable = false, unique = true)
    @Required
    private String nombre;

    @Column()
    private String descripcion;
}