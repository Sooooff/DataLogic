package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;
import org.openxava.calculators.CurrentYearCalculator;

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
public class MetaVenta extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre, cif")
    @Required
    private Empleado empleado;

    @Required
    @org.openxava.annotations.DefaultValueCalculator(CurrentYearCalculator.class)
    private int anio;

    @Required
    private int mes;

    @Money
    @Required
    private BigDecimal montoMeta;

    private Boolean cumplida = false;
}