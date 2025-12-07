package org.example.DataLogic.actions;

import org.example.DataLogic.model.Cotizacion;
import org.example.DataLogic.model.Factura;
import org.example.DataLogic.model.LineItem;
import org.openxava.actions.ViewBaseAction;
import org.openxava.view.View;
import org.openxava.jpa.XPersistence;

public class ConvertirCotizacionAFacturaAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        View view = getView();

        if (view.isKeyEditable()) {
            addError("Primero debes seleccionar una Cotización.");
            return;
        }

        // SINTAXIS CORREGIDA: No usar name:
        Long cotizacionId = (Long) view.getValue("id");
        Cotizacion cotizacion = XPersistence.getManager().find(Cotizacion.class, cotizacionId);

        if (cotizacion == null) {
            // SINTAXIS CORREGIDA: No usar messageId:
            addError("Cotización no encontrada.");
            return;
        }

        // MÉTODO CORRECTO: isConvertidoAFactura()
        if (cotizacion.isConvertidoAFactura()) {
            addError("La Cotización #%d ya fue convertida a Factura.", cotizacion.getNumeroCotizacion());
            return;
        }

        // PRIMER PASO: Selección de Empleado
        if (!view.getModelName().equals("Factura")) {

            view.setModelName("Factura");
            view.setEditable(true);
            view.setViewName("SeleccionEmpleado"); // Usa la vista del modelo Factura

            addMessage("Por favor, selecciona el Vendedor que cerró esta venta.");
            return;
        }

        // SEGUNDO PASO: Convertir a Factura
        // SINTAXIS CORREGIDA: No usar name:
        Long empleadoId = (Long) view.getValue("empleado.id");
        if (empleadoId == null) {
            addError("Debes seleccionar un Empleado (Vendedor) para generar la Factura.");
            return;
        }

        // 1. Crear la Factura
        Factura factura = new Factura();
        factura.setCotizacionOrigen(cotizacion);
        factura.setEmpleado(XPersistence.getManager().find(org.example.DataLogic.model.Empleado.class, empleadoId));

        // 2. Clonar los ítems
        for (LineItem item : cotizacion.getItems()) {
            LineItem newItem = new LineItem();
            newItem.setProducto(item.getProducto());
            newItem.setCantidad(item.getCantidad());
            newItem.setPrecioUnitario(item.getPrecioUnitario());
            factura.getItems().add(newItem);
        }

        // 3. Persistir y actualizar Cotizacion
        XPersistence.getManager().persist(factura);
        cotizacion.setConvertidoAFactura(true);
        XPersistence.getManager().merge(cotizacion);

        // 4. Mostrar Factura
        addMessage("Cotización #%d convertida exitosamente a Factura por el Vendedor: %s",
                cotizacion.getNumeroCotizacion(), factura.getEmpleado().getNombre());

        view.setModelName("Factura");
        view.setKeyEditable(false);
        view.setValue("id", factura.getId());
    }
}