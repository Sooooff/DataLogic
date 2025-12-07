package org.example.DataLogic.controllers;

import org.example.DataLogic.model.MetaVenta;
import org.openxava.actions.TabBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.tab.Tab;

import javax.persistence.Query;
import java.math.BigDecimal;

public class CalcularCumplimientoController extends TabBaseAction {

    @Override
    public void execute() throws Exception {
        Tab tab = getTab();

        // CORRECCIÓN: En OpenXava 7.6.1, usamos getSelectedKeys()
        Object[] selectedKeys = tab.getSelectedKeys();

        for (Object key : selectedKeys) {
            MetaVenta meta = XPersistence.getManager().find(MetaVenta.class, key);

            if (meta != null) {
                // CORRECCIÓN: Calculamos el total de ventas directamente
                Query query = XPersistence.getManager().createQuery(
                        "SELECT SUM(li.precioUnitario * li.cantidad) " +
                                "FROM Factura f JOIN f.items li " +
                                "WHERE f.empleado.id = :empleadoId " +
                                "AND f.anio = :anio AND f.mes = :mes"
                );

                query.setParameter("empleadoId", meta.getEmpleado().getId());
                query.setParameter("anio", meta.getAnio());
                query.setParameter("mes", meta.getMes());

                Double ventasTotalesDouble = (Double) query.getSingleResult();
                BigDecimal ventasTotales = ventasTotalesDouble != null ?
                        BigDecimal.valueOf(ventasTotalesDouble) : BigDecimal.ZERO;

                // Determinar si se cumplió la meta
                boolean cumplida = ventasTotales.compareTo(meta.getMontoMeta()) >= 0;

                // Actualizar la meta
                meta.setCumplida(cumplida);
                XPersistence.getManager().merge(meta);

                // Mensaje informativo
                addMessage("Meta para " + meta.getEmpleado().getNombre() +
                        " - " + meta.getAnio() + "/" + meta.getMes() +
                        ": Ventas = C$" + ventasTotales +
                        ", Meta = C$" + meta.getMontoMeta() +
                        ", Cumplida = " + (cumplida ? "SÍ" : "NO"));
            }
        }

        // Refrescar la tabla
        tab.reset();
    }
}