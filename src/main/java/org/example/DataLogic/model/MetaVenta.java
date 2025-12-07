package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames={"empleado_id", "anio", "mes"})
})
@View(name = "ConControl",
        members = "empleado; anio, mes; montoMeta [montoMeta]; cumplida")
public class MetaVenta extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre, cif")
    @Required
    @Editor("empleado_vendedor")  // Usa nuestro control personalizado
    private Empleado empleado;

    @Required
    @DefaultValueCalculator(org.openxava.calculators.CurrentYearCalculator.class)
    private int anio;

    @Required
    private int mes;

    @Money
    @Required
    @Editor("moneda_formato")  // Usa nuestro control personalizado para moneda
    private BigDecimal montoMeta;

    @ReadOnly
    private Boolean cumplida = false;
}