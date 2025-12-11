package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;

import javax.persistence.Query;

public class CalculatorNumeroOrden implements ICalculator {

    @Getter @Setter
    int anioFiscal;

    @Override
    public Object calculate(){
        Integer numero = 1;
        Query query = XPersistence.getManager()
                .createQuery("SELECT max(o.numeroOrden) FROM OrdenCompra o");

        Integer maxNumero = (Integer) query.getSingleResult();
        return (maxNumero == null) ? 1 : maxNumero + 1;
    }
}