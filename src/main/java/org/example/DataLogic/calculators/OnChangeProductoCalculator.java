package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.model.Producto;
import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;

import java.math.BigDecimal;


@Getter
@Setter
public class OnChangeProductoCalculator implements ICalculator {

    private Producto producto;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public Object calculate() throws Exception {
        if (producto != null && producto.getId() != null) {
            // Recargar el producto para asegurar que tenemos el precio actual
            Producto productoActual = XPersistence.getManager()
                    .find(Producto.class, producto.getId());
            if (productoActual != null) {
                return productoActual.getPrecioUnitario();
            }
        }
        return BigDecimal.ZERO;
    }
}