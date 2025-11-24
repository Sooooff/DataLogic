package org.example.DataLogic;

import org.example.DataLogic.controller.*;
import org.example.DataLogic.model.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        // controllers
        EmpleadoController empCtrl = new EmpleadoController();
        OrdenController ordenCtrl = new OrdenController();
        FacturaController facCtrl = new FacturaController();
        CotizacionController cotCtrl = new CotizacionController();

        // crear empleados (ejemplo)
        Empleado e1 = empCtrl.createEmpleado("23020138", "Nathaly V. A. R.", "Vendedor");
        Empleado e2 = empCtrl.createEmpleado("23020091", "Ana Sofía F. V.", "Gerente");

        // asignar meta a vendedor (mensual por ejemplo)
        empCtrl.asignarMeta(e1.getId(), 1000.00);

        // productos
        Producto p1 = new Producto("P001", "Laptop básica", 350.0);
        Producto p2 = new Producto("P002", "Mouse", 10.0);

        // crear orden (hecha por empleado e1)
        OrdenCompra orden = ordenCtrl.createOrden(Arrays.asList(
                new LineItem(p1, 2),
                new LineItem(p2, 5)
        ), e1.getId());

        System.out.println("ORDEN CREADA:");
        System.out.println(orden);

        // convertir orden a factura (botón) — factura registrará el empleado que vendió
        System.out.println("\nConvirtiendo orden a factura...");
        Factura factura = facCtrl.createFacturaDesdeOrden(orden);
        System.out.println("FACTURA CREADA:");
        System.out.println(factura);

        // crear cotizacion (sin vendedor asignado en este ejemplo)
        Cotizacion cot = cotCtrl.createCotizacion(Arrays.asList(new LineItem(p2, 3)));
        System.out.println("\nCOTIZACION CREADA:");
        System.out.println(cot);

        // convertir cotizacion -> factura (asignar empleado manualmente)
        System.out.println("\nConvirtiendo cotizacion a factura (asignando a empleado e1)...");
        Factura factura2 = facCtrl.createFacturaDesdeCotizacion(cot, e1.getId());
        System.out.println("FACTURA CREADA:");
        System.out.println(factura2);

        // reportes: ventas por empleado
        System.out.println("\nVENTAS POR EMPLEADO:");
        System.out.println("Total vendido por " + e1.getNombre() + ": " +
                facCtrl.getReportService().getTotalVendidoPorEmpleado(e1.getId()));

        // verificar metas
        boolean cumplio = empCtrl.verificarMetaCumplida(e1.getId());
        System.out.println("\n¿Empleado " + e1.getNombre() + " cumplió su meta? " + (cumplio ? "Sí" : "No"));

        System.out.println("\n--- FIN DEMO ---");
    }
}
