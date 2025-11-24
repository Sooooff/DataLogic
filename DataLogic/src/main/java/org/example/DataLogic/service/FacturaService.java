package org.example.DataLogic.service;

import org.example.DataLogic.calculators.CalculatorNumeroOrden;
import org.example.DataLogic.model.*;
import org.example.DataLogic.repository.FacturaRepository;

public class FacturaService {
    private final FacturaRepository repo = new FacturaRepository();

    public Factura createFacturaFromOrden(OrdenCompra orden) {
        String numero = CalculatorNumeroOrden.generarNumero("FAC");
        // la factura heredará el empleadoId de la orden si está presente
        Factura f = new Factura(numero, orden.getEmpleadoId());
        orden.getItems().forEach(i -> f.addItem(new LineItem(i.getProducto(), i.getCantidad())));
        f.setOrdenOrigenId(orden.getId());
        repo.save(f);
        orden.setConvertidoAInvoice(true);
        return f;
    }

    public Factura createFacturaFromCotizacion(Cotizacion cot, Long empleadoId) {
        String numero = CalculatorNumeroOrden.generarNumero("FAC");
        Factura f = new Factura(numero, empleadoId);
        cot.getItems().forEach(i -> f.addItem(new LineItem(i.getProducto(), i.getCantidad())));
        f.setCotizacionOrigenId(cot.getId());
        repo.save(f);
        return f;
    }

    public Factura findById(long id) { return repo.findById(id); }

    public java.util.Collection<Factura> findAll() { return repo.findAll(); }
}
