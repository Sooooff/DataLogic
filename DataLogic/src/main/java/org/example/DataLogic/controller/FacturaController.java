package org.example.DataLogic.controller;

import org.example.DataLogic.model.Factura;
import org.example.DataLogic.model.OrdenCompra;
import org.example.DataLogic.service.FacturaService;
import org.example.DataLogic.service.ReportService;

public class FacturaController {
    private final FacturaService service = new FacturaService();
    private final ReportService reportService = new ReportService(service);

    // convertir orden -> factura
    public Factura createFacturaDesdeOrden(OrdenCompra orden) {
        Factura f = service.createFacturaFromOrden(orden);
        System.out.println("FACTURA GENERADA:\n" + f);
        return f;
    }

    // convertir cotizacion -> factura (se requiere asignar empleadoId aquí)
    public Factura createFacturaDesdeCotizacion(Cotizacion cot, Long empleadoId) {
        Factura f = service.createFacturaFromCotizacion(cot, empleadoId);
        System.out.println("FACTURA GENERADA:\n" + f);
        return f;
    }

    public ReportService getReportService() { return reportService; }
}
