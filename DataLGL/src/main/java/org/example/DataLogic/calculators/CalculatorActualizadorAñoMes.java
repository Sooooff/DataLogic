package org.example.DataLogic.calculators;

public class CalculatorActualizadorAñoMes {
}
package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.model.Empleado;
import org.openxava.calculators.ICalculator;

import java.time.LocalDate;

@Setter
@Getter
public class CalculatorActualizarAñoMes implements ICalculator {

    private Empleado empleado;

    @Override
    public Object calculate() throws Exception {
        LocalDate hoy = LocalDate.now();
        return new int[]{hoy.getYear(), hoy.getMonthValue()};
    }
}