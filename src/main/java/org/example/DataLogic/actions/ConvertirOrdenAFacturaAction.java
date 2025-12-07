package org.example.DataLogic.actions;

import org.example.DataLogic.model.*;
import org.openxava.actions.ViewBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.view.View;


public class ConvertirOrdenAFacturaAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        View view = getView();

        if (view.isKeyEditable()) {
            addError("Selecciona una Orden de Compra antes de continuar.");
            return;
        }

        Long ordenId = (Long) view.getValue("id");
        OrdenCompra orden = XPersistence.getManager().find(OrdenCompra.class, ordenId);

        if (orden == null) {
            addError("La Orden #" + ordenId + " no existe.");
            return;
        }

        if (orden.isConvertidoAFactura()) {
            addError("La Orden #" + orden.getNumeroOrden() + " ya fue convertida a Factura.");
            return;
        }

        // Mostrar vista para seleccionar empleado
        if (!view.getModelName().equals("Factura")) {
            view.setModelName("Factura");
            view.setEditable(true);
            view.setViewName("SeleccionEmpleado");
            addMessage("Selecciona el Vendedor que realizará la factura.");
            return;
        }

        // Obtener empleado seleccionado
        Long empleadoId = (Long) view.getValue("empleado.id");
        if (empleadoId == null) {
            addError("Debes seleccionar un Vendedor para generar la Factura.");
            return;
        }

        Empleado empleado = XPersistence.getManager().find(Empleado.class, empleadoId);

        // Crear factura
        Factura factura = new Factura();
        factura.setOrdenOrigen(orden);
        factura.setEmpleado(empleado);
        factura.setAnioFiscal(orden.getAnioFiscal());

        // Copiar ítems
        for (LineItem item : orden.getItems()) {
            LineItem nuevoItem = new LineItem();
            nuevoItem.setProducto(item.getProducto());
            nuevoItem.setCantidad(item.getCantidad());
            nuevoItem.setPrecioUnitario(item.getPrecioUnitario());
            factura.getItems().add(nuevoItem);
        }

        // Persistir
        XPersistence.getManager().persist(factura);

        // Marcar orden como convertida
        orden.setConvertidoAFactura(true);
        XPersistence.getManager().merge(orden);

        // Mostrar mensaje de éxito
        addMessage("Orden #" + orden.getNumeroOrden() +
                " convertida a Factura #" + factura.getNumeroFactura() +
                " para el vendedor: " + empleado.getNombre());

        // Mostrar la factura creada
        view.setModelName("Factura");
        view.setKeyEditable(false);
        view.setValue("id", factura.getId());
    }
}