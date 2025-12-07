package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroFactura;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;
import org.openxava.calculators.CurrentYearCalculator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
public class Cotizacion extends BaseEntity {

    @DefaultValueCalculator(CurrentYearCalculator.class)
    @ReadOnly
    private int anioFiscal;

    @DefaultValueCalculator(
            value= CalculatorNumeroFactura.class,
            properties = {
                    @PropertyValue(name="anioFiscal"),
                    @PropertyValue(name="entidad", value="Cotizacion")
            }
    )
    @ReadOnly
    private int numeroCotizacion;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

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
    private Long empleadoAsignado;


    @Money
    @Depends("items.subtotal")
    public BigDecimal getTotal() {
        if (items == null) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}