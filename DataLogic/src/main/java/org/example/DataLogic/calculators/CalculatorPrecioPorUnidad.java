package org.example.DataLogic.calculators;

import org.example.DataLogic.model.LineItem;

public class CalculatorPrecioPorUnidad {
    public static double calcularSubtotal(LineItem item) {
        return item.getCantidad() * item.getPrecioUnitario();
    }
}
