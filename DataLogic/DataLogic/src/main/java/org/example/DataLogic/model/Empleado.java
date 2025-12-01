package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Empleado extends BaseEntity {

    @Required
    private String cif; // CI o código

    @Required
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required
    private Rol rol;

    // Campo de metaVenta ELIMINADO. Ahora usa MetaVenta.java

    private Boolean activo = true;
}