package org.example.DataLogic.controllers;

import lombok.Setter;
import org.openxava.actions.TabBaseAction;
import org.openxava.tab.Tab;

@Setter
public class FiltrarPorVendedorController extends TabBaseAction {

    private Long empleadoId;

    @Override
    public void execute() throws Exception {
        Tab tab = getTab();

        if (empleadoId != null && empleadoId > 0) {
            // Aplicar filtro por empleado
            tab.setBaseCondition("${empleado.id} = " + empleadoId);
        } else {
            // Mostrar todos los registros
            tab.setBaseCondition("");
        }

        // En OpenXava 7.6.1, la tabla se refresca automáticamente al cambiar la condición
        // No es necesario llamar a search() manualmente
    }
}