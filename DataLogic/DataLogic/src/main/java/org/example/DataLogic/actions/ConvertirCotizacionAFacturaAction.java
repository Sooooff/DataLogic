package org.example.DataLogic.actions;

import org.example.DataLogic.model.Factura;
import org.example.DataLogic.model.LineItem;
import org.openxava.actions.ViewBaseAction;
import org.openxava.jpa.XPersistence;

public class ConvertirCotizacionAFacturaAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {

        if (getView().isKeyEditable()) {
            addError("Selecciona una Cotización primero.");
            return;
        }

        Long id = (Long) getView().getValue("id");
        Cotizacion cot = XPersistence.getManager().find(Cotizacion.class, id);

        if (cot == null) {
            addError("No existe esta cotización.");
            return;
        }

        if (cot.isConvertidoAFactura()) {
            addError("La Cotización %s ya fue convertida anteriormente.", cot.getNumeroCotizacion());
            return;
        }

        Long empleadoId = (Long) getView().getValue("empleadoId");
        if (empleadoId == null) {
            addError("Debes seleccionar un Empleado antes de convertir.");
            return;
        }

        // Crear factura
        Factura factura = new Factura();
        factura.setNumeroFactura(Integer.parseInt("FAC-" + factura.getId()));
        factura.setCotizacionOrigenId(cot.getId());
        factura.setEmpleadoId(empleadoId);

        // copiar items
        for (LineItem item : cot.getItems()) {
            LineItem nuevo = new LineItem();
            nuevo.setProducto(item.getProducto());
            nuevo.setCantidad(item.getCantidad());
            nuevo.setPrecioUnitario(item.getPrecioUnitario());
            factura.getItems().add(nuevo);
        }

        XPersistence.getManager().persist(factura);

        cot.setConvertidoAFactura(true);
        XPersistence.getManager().merge(cot);

        addMessage("Cotización %s convertida exitosamente a Factura %s",
                cot.getNumeroCotizacion(), factura.getNumeroFactura());

        getView().setModel(factura);
    }
}
