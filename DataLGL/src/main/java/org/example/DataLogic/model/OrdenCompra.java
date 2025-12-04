package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroOrden;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;
import org.openxava.calculators.CurrentYearCalculator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
public class OrdenCompra extends BaseEntity {

    @DefaultValueCalculator(CurrentYearCalculator.class)
    @ReadOnly
    private int anioFiscal;

    @DefaultValueCalculator(
            value= CalculatorNumeroOrden.class,
            properties = @PropertyValue(name="anioFiscal")
    )
    @ReadOnly
    private int numeroOrden;


    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required
    private Empleado empleado;

    @ElementCollection
    @ListProperties(
            "producto.nombre" +
                    ",precioUnitario" +
                    ",cantidad" +
                    ",subtotal"
    )
    private Collection<LineItem> items = new ArrayList<>();

    @Hidden
    private boolean convertidoAFactura;
    private Long empleadoId; // vendedor que creó la orden


    @Money
    @Depends("items.subtotal")
    public BigDecimal getTotal() {
        if (items == null) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isConvertidoAInvoice() {
        return false;
    }

    public void setConvertidoAInvoice(boolean b) {
    }
}