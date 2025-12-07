package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.calculators.CalculatorPrecioUnitario;
import org.example.DataLogic.calculators.OnChangeProductoCalculator;
import org.openxava.annotations.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Embeddable
@Getter @Setter
public class LineItem {

    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre, precioUnitario")
    @Required
    @OnChange(OnChangeProductoCalculator.class)
    private Producto producto;

    @Money
    @DefaultValueCalculator(
            value = CalculatorPrecioUnitario.class,
            properties = @PropertyValue(
                    name = "productoId",
                    from = "producto.id"
            )
    )
    private BigDecimal precioUnitario;

    @Money
    @Depends("precioUnitario, cantidad")
    public BigDecimal getSubtotal() {
        if (precioUnitario == null || cantidad <= 0) {
            return BigDecimal.ZERO;
        }
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }
}