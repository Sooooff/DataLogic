package org.example.DataLogic.controller;

import org.example.DataLogic.model.Empleado;
import org.example.DataLogic.service.EmpleadoService;
import org.example.DataLogic.service.FacturaService;
import org.example.DataLogic.service.ReportService;

import java.time.LocalDate;

public class EmpleadoController {
    private final EmpleadoService empService = new EmpleadoService();
    private final FacturaService facturaService = new FacturaService();
    private final ReportService reportService = new ReportService(facturaService);

    public Empleado createEmpleado(String cif, String nombre, String rol) {
        return empService.createEmpleado(cif, nombre, rol);
    }

    public Empleado findEmpleado(long id) { return empService.findById(id); }

    public void asignarMeta(long empleadoId, double monto) {
        Empleado e = empService.findById(empleadoId);
        if (e != null) {
            e.setMetaVenta(monto);
            empService.save(e);
        }
    }

    public boolean verificarMetaCumplida(long empleadoId) {
        Empleado e = empService.findById(empleadoId);
        if (e == null || e.getMetaVenta() == null) return false;
        double totalVendido = reportService.getTotalVendidoPorEmpleado(empleadoId);
        return totalVendido >= e.getMetaVenta();
    }
}
