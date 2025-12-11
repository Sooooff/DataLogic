package org.example.DataLogic.actions;

import org.openxava.actions.ViewBaseAction;

public class VerReporteCumplimientoAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        Long empleadoId = (Long) getView().getValue("empleado.id");

        if (empleadoId == null) {
            addError("Debe seleccionar un empleado para generar el reporte");
            return;
        }

        addMessage("Generando reporte de cumplimiento...");

        // JavaScript para abrir ventana emergente con reporte
        String js = "<script>"
                + "window.open('/DataLogic/reportes/cumplimiento.jsp?empleadoId=" + empleadoId + "', "
                + "'_blank', 'width=900,height=700,scrollbars=yes');"
                + "</script>";

        getRequest().setAttribute("javascript", js);
        addMessage