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
        Object[] selectedKeys = tab.getSelectedKeys();

        for (Object key : selectedKeys) {
            MetaVenta meta = XPersistence.getManager().find(MetaVenta.class, key);

            if (meta != null) {
                // Calcular ventas del mes
                Query query = XPersistence.getManager().createQuery(
                        "SELECT COALESCE(SUM(f.total), 0) FROM Factura f " +
                                "WHERE f.empleado.id = :empleadoId " +
                                "AND YEAR(f.fecha) = :anio " +
                                "AND MONTH(f.fecha) = :mes"
                );

                query.setParameter("empleadoId", meta.getEmpleado().getId());
                query.setParameter("anio", meta.getAnio());
                query.setParameter("mes", meta.getMes());

                Object resultado = query.getSingleResult();
                BigDecimal ventasTotales = BigDecimal.ZERO;

                if (resultado instanceof Double) {
                    ventasTotales = BigDecimal.valueOf((Double) resultado);
                } else if (resultado instanceof BigDecimal) {
                    ventasTotales = (BigDecimal) resultado;
                }

                // Determinar si se cumplió la meta
                boolean cumplida = ventasTotales.compareTo(meta.getMontoMeta()) >= 0;

                // Actualizar la meta
                meta.setVentasRealizadas(ventasTotales);
                meta.setCumplida(cumplida);
                XPersistence.getManager().merge(meta);

                // Mensaje informativo
                addMessage("Meta para " + meta.getEmpleado().getNombreCompleto() +
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