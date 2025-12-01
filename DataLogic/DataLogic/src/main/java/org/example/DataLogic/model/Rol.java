package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Rol extends BaseEntity {

    @Required
    private String nombre;

    private String descripcion;
}