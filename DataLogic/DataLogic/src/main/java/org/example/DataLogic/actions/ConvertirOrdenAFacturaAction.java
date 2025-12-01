package org.example.DataLogic.actions;

import org.example.DataLogic.model.Factura;
import org.example.DataLogic.model.LineItem;
import org.example.DataLogic.model.OrdenCompra;
import org.openxava.actions.ViewBaseAction;
import org.openxava.jpa.XPersistence;

public class ConvertirOrdenAFacturaAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {

        if (getView().isKeyEditable()) {
            addError("Selecciona una Orden de Compra antes de continuar.");
            return;
        }

        Long ordenId = (Long) getView().getValue("id");
        OrdenCompra orden = XPersistence.getManager().find(OrdenCompra.class, ordenId);

        if (orden == null) {
            addError("La Orden no existe.");
            return;
        }

        if (orden.isConvertidoAInvoice()) {
            addError("La Orden %s ya fue convertida anteriormente.", orden.getNumeroOrden());
            return;
        }

        // Crear factura
        Factura factura = new Factura();
        factura.setNumeroFactura(Integer.parseInt("FAC-" + factura.getId()));
        factura.setOrdenOrigenId(orden.getId());
        factura.setEmpleadoId(orden.getEmpleadoId());

        // copiar items
        for (LineItem item : orden.getItems()) {
            LineItem nuevo = new LineItem();
            nuevo.setProducto(item.getProducto());
            nuevo.setCantidad(item.getCantidad());
            nuevo.setPrecioUnitario(item.getPrecioUnitario());
            factura.getItems().add(nuevo);
        }

        XPersistence.getManager().persist(factura);

        // marcar orden como convertida
        orden.setConvertidoAInvoice(true);
        XPersistence.getManager().merge(orden);

        addMessage("Orden %s convertida correctamente a factura %s",
                orden.getNumeroOrden(), factura.getNumeroFactura());

        getView().setModel(factura);
    }
}
