package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;

import javax.persistence.Query;

public class CalculatorNumeroFactura implements ICalculator {

    @Getter @Setter
    int anioFiscal;

    @Getter @Setter
    String entidad; // Usado para buscar en la tabla correcta (Factura o Cotizacion)

    @Override
    public Object calculate() throws Exception {
        if (entidad == null || entidad.isEmpty()) {
            throw new IllegalArgumentException("La propiedad 'entidad' es requerida.");
        }

        String campoNumero = entidad.equals("Cotizacion") ? "numeroCotizacion" : "numeroFactura";

        Integer numero = 1;
        Query query = XPersistence.getManager()
                .createQuery("SELECT max(f." + campoNumero + ")" +
                        " FROM " + entidad + " f " +
                        " WHERE f.anioFiscal = :anioFiscal");
        query.setParameter("anioFiscal", anioFiscal);

        Integer maxNumero = (Integer)query.getSingleResult();
        return (maxNumero == null) ? 1 : maxNumero + 1;
    }
}