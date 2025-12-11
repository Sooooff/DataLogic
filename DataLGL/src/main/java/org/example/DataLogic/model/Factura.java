package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroFactura;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
@Table(name = "factura")
@Views({
        @View(name = "Simple", members = "numeroFactura, fecha, empleado, cliente, total"),
        @View(name = "SeleccionEmpleado", members = "empleado; cliente"),
        @View(name = "Completa", members = "numeroFactura, fecha; empleado; cliente; items; total; ordenOrigen")
})
@Tabs({
        @Tab(name = "Todas",
                properties = "numeroFactura, fecha, empleado.nombreCompleto, cliente.nombreCompleto, total",
                defaultOrder = "${fecha} desc"),
        @Tab(name = "PorVendedor",
                properties = "numeroFactura, fecha, empleado.nombreCompleto, cliente.nombreCompleto, total",
                baseCondition = "${empleado.id} = ?"),
        @Tab(name = "ParaMetas",
                properties = "numeroFactura, fecha, empleado.nombreCompleto, total",
                defaultOrder = "${fecha} desc")
})
public class Factura extends BaseEntity {

    @DefaultValueCalculator(
            value = CalculatorNumeroFactura.class,
            properties = @PropertyValue(name = "entidad", value = "Factura")
    )
    @ReadOnly
    private int numeroFactura;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombreCompleto")
    @Required
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombreCompleto")
    @Required
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    @ReadOnly
    private OrdenCompra ordenOrigen;

    @ElementCollection
    @ListProperties("producto.nombre, precioUnitario, cantidad, subtotal")
    private Collection<ItemLinea> items = new ArrayList<>();

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