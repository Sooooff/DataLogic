package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;
import org.openxava.annotations.TextArea;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Producto extends BaseEntity{

    @Required
    private String codigo;

    @Required
    private String nombre;

    @TextArea
    private String descripcion;

    @Money
    @Required
    private BigDecimal precioUnitario;
}