package org.example.DataLogic.calculators;

import lombok.Getter;
import lombok.Setter;
import org.example.DataLogic.model.Empleado;
import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;

import java.time.LocalDate;

@Setter
@Getter
public class CalculatorActualizarAnioMes implements ICalculator {

    private Empleado empleado;

    @Override
    public Object calculate() throws Exception {
        // Este calculator se usa en @OnChange para actualizar año y mes cuando cambia el empleado
        // En realidad, queremos actualizar año y mes de la fecha actual
        LocalDate hoy = LocalDate.now();

        // Retornamos un arreglo con [año, mes]
        return new int[]{hoy.getYear(), hoy.getMonthValue()};
    }
}
