package org.example.DataLogic.controllers;

import lombok.Setter;
import org.openxava.actions.TabBaseAction;
import org.openxava.tab.Tab;

@Setter
public class FiltrarPorVendedorController extends TabBaseAction {

    private Long empleadoId;

    @Override
    public void execute(){
        Tab tab = getTab();

        if (empleadoId != null && empleadoId > 0) {
            tab.setBaseCondition("${empleado.id} = " + empleadoId);
        } else {
            tab.setBaseCondition("");
        }
    }
}