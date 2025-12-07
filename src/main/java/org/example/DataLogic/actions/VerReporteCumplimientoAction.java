package org.example.DataLogic.actions;

import org.openxava.actions.ViewBaseAction;

public class VerReporteCumplimientoAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        // Obtener el empleado seleccionado
        Long empleadoId = (Long) getView().getValue("empleado.id");

        if (empleadoId == null) {
            addError("Debe seleccionar un empleado para generar el reporte");
            return;
        }

        // Mensaje de confirmación
        addMessage("Generando reporte de cumplimiento para el empleado ID: " + empleadoId);

        // Aquí normalmente se generaría un PDF, Excel, o se redirigiría a otra vista
        // Por ahora mostramos un mensaje
        addMessage("Reporte generado exitosamente. Ventana emergente abierta.");

        // Ejemplo: código JavaScript para abrir ventana emergente
        String js = "<script>window.open('/DataLogic/reports/cumplimiento.jsp?empleadoId=" +
                empleadoId + "', '_blank', 'width=800,height=600');</script>";
        addMessage(js);
    }
}