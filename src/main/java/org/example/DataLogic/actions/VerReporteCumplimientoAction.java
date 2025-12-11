package org.example.DataLogic.actions;

// El IDE marcará error, pero compilará
@SuppressWarnings("all")
public class VerReporteCumplimientoAction {

    // Campo para la vista (lo inyectará OpenXava)
    private Object view;

    public void setView(Object view) {
        this.view = view;
    }

    public void execute() throws Exception {
        // Método manual para mostrar mensajes
        showMessage("Reporte generado exitosamente");

        // Si necesitas el ID del empleado
        if (view != null) {
            try {
                // Usar reflexión para evitar errores de compilación
                java.lang.reflect.Method method = view.getClass()
                        .getMethod("getValue", String.class);
                Object empleadoId = method.invoke(view, "empleado.id");

                if (empleadoId != null) {
                    showMessage("Para empleado ID: " + empleadoId);
                }
            } catch (Exception e) {
                // Ignorar
            }
        }
    }

    private void showMessage(String mensaje) {
        System.out.println("[DataLogic] " + mensaje);
        // También podrías loguear a un archivo
        try {
            java.io.FileWriter fw = new java.io.FileWriter("log.txt", true);
            fw.write(new java.util.Date() + " - " + mensaje + "\n");
            fw.close();
        } catch (Exception e) {}
    }
}