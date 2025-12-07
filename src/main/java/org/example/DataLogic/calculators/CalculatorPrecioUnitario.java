package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.model.Producto;
import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Setter
@Getter
public class CalculatorPrecioUnitario implements ICalculator {

    private Long productoId;

    @Override
    public Object calculate() throws Exception {
        if (productoId == null) {
            return BigDecimal.ZERO;
        }

        try {
            Producto producto = XPersistence.getManager().find(Producto.class, productoId);
            if (producto != null && producto.getPrecioUnitario() != null) {
                return producto.getPrecioUnitario();
            }
        } catch (EntityNotFoundException e) {
            // Producto no encontrado, retornar cero
        }

        return BigDecimal.ZERO;
    }
}