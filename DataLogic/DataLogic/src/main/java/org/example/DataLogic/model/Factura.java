package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorNumeroFactura;
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
@View(name="Report", members = "numeroFactura, fecha, empleado;" +
        "total;" +
        "ordenOrigen, cotizacionOrigen")
// NUEVA VISTA PARA SELECCIONAR SOLO EL EMPLEADO EN LA ACCIÓN
@View(name="SeleccionEmpleado", members = "empleado")
public class Factura extends BaseEntity {

    @DefaultValueCalculator(CurrentYearCalculator.class)
    @ReadOnly
    private int anioFiscal;

    @DefaultValueCalculator(
            value= CalculatorNumeroFactura.class,
            properties = {
                    @PropertyValue(name="anioFiscal"),
                    @PropertyValue(name="entidad", value="Factura")
            }
    )
    @ReadOnly
    private int numeroFactura;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    @Required
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Report")
    private OrdenCompra ordenOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cotizacion cotizacionOrigen;
    private Long ordenOrigenId;
    private Long empleadoId;


    @ElementCollection
    @ListProperties(
            "producto.nombre" +
                    ",precioUnitario" +
                    ",cantidad" +
                    ",subtotal"
    )
    private Collection<LineItem> items = new ArrayList<>();

    @Money
    @Depends("items.subtotal")
    public BigDecimal getTotal() {
        if (items == null) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setCotizacionOrigenId(Long id) {
    }
}