package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroFactura;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;
import org.openxava.calculators.CurrentYearCalculator;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@View(name = "Report", members = "numeroFactura, fecha, empleado; total; ordenOrigen, cotizacionOrigen")
@View(name = "SeleccionEmpleado", members = "empleado")
@View(name = "ConFiltro",
        members = "numeroFactura, fecha, empleado; total; ordenOrigen, cotizacionOrigen")
@View(name = "ConControlMoneda",  // MOVIDO A NIVEL DE CLASE
        members = "numeroFactura, fecha; empleado; items; total; ordenOrigen, cotizacionOrigen")
@Tab(name = "PorVendedor",
        properties = "numeroFactura, fecha, empleado.nombre, total",
        baseCondition = "${empleado.id} = ?")
@Tab(name = "ParaMetas",
        properties = "numeroFactura, fecha, empleado.nombre, total, anio, mes",
        defaultOrder = "${anio} desc, ${mes} desc")
@Tab(name = "FiltroVendedor",
        properties = "numeroFactura, fecha, empleado.nombre, total",
        baseCondition = "1 = 1")
public class Factura extends BaseEntity {

    @DefaultValueCalculator(CurrentYearCalculator.class)
    @ReadOnly
    private int anioFiscal;

    @DefaultValueCalculator(
            value = CalculatorNumeroFactura.class,
            properties = {
                    @PropertyValue(name = "anioFiscal"),
                    @PropertyValue(name = "entidad", value = "Factura")
            }
    )
    @ReadOnly
    private int numeroFactura;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @Column(length = 4)
    private int anio;

    @Column(length = 2)
    private int mes;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Report")
    private OrdenCompra ordenOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cotizacion cotizacionOrigen;

    @ElementCollection
    @ListProperties("producto.nombre, precioUnitario, cantidad, subtotal")
    private Collection<LineItem> items = new ArrayList<>();

    @Money
    @Depends("items.subtotal")
    public BigDecimal getTotal() {
        if (items == null) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    public void prePersist() {
        if (fecha != null) {
            this.anio = fecha.getYear();
            this.mes = fecha.getMonthValue();
        }
    }
}