package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.model.Empleado;
import org.openxava.calculators.ICalculator;

import java.time.LocalDate;

@Setter
@Getter
public class CalculatorActualizarAnioMes implements ICalculator {

    private Empleado empleado;

    @Override
    public Object calculate(){
        LocalDate hoy = LocalDate.now();
        return new int[]{hoy.getYear(), hoy.getMonthValue()};
    }
}