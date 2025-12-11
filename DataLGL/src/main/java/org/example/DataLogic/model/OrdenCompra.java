package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroOrden;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
@Table(name = "orden_compra")
@Views({
        @View(name = "Simple", members = "numeroOrden, fecha, cliente, total"),
        @View(name = "ParaFactura", members = "numeroOrden, fecha, cliente, items, total")
})
@Tabs({
        @Tab(name = "Todas", properties = "numeroOrden, fecha, cliente.nombreCompleto, total"),
        @Tab(name = "Pendientes",
                properties = "numeroOrden, fecha, cliente.nombreCompleto, total",
                baseCondition = "${convertidoAFactura} = false")
})
public class OrdenCompra extends BaseEntity {

    @DefaultValueCalculator(
            value = CalculatorNumeroOrden.class,
            properties = @PropertyValue(name = "anioFiscal", from = "fecha.year")
    )
    @ReadOnly
    private int numeroOrden;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombreCompleto")
    @Required
    private Cliente cliente;

    @ElementCollection
    @ListProperties("producto.nombre, precioUnitario, cantidad, subtotal")
    private Collection<ItemLinea> items = new ArrayList<>();

    @Column(name = "convertido_a_factura")
    @Hidden
    private boolean convertidoAFactura = false;

    @Money
    @ReadOnly
    @Depends("items.subtotal")
    public BigDecimal getTotal() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(ItemLinea::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}