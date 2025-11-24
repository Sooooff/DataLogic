package org.example.DataLogic.service;

import org.example.DataLogic.model.Factura;

import java.util.Collection;

/**
 * Servicio para reportes: total vendido por empleado.
 */
public class ReportService {
    private final FacturaService facturaService;

    public ReportService(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    public double getTotalVendidoPorEmpleado(Long empleadoId) {
        Collection<Factura> facturas = facturaService.findAll();
        return facturas.stream()
                .filter(f -> f.getEmpleadoId() != null && f.getEmpleadoId().equals(empleadoId))
                .mapToDouble(Factura::getTotal)
                .sum();
    }
}
