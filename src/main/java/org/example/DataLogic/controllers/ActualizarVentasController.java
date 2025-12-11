package org.example.DataLogic.controllers;

import org.example.DataLogic.model.MetaVenta;
import org.openxava.actions.TabBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.tab.Tab;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class ActualizarVentasController extends TabBaseAction {

    @Override
    public void execute() throws Exception {
        Tab tab = getTab();

        // Obtener todas las metas
        Query query = XPersistence.getManager().createQuery(
                "SELECT m FROM MetaVenta m"
        );
        List<MetaVenta> todasLasMetas = query.getResultList();

        int actualizadas = 0;

        for (MetaVenta meta : todasLasMetas) {
            // CONSULTA CORREGIDA: Suma precioUnitario * cantidad
            Query ventasQuery = XPersistence.getManager().createQuery(
                    "SELECT COALESCE(SUM(l1.precioUnitario * l1.cantidad), 0) " +
                    "FROM Factura f JOIN f.items l1 " +
                    "WHERE f.empleado.id = :empleadoId " +
                    "AND YEAR(f.fecha) = :anio " +
                    "AND MONTH(f.fecha) = :mes"
            );


            ventasQuery.setParameter("empleadoId", meta.getEmpleado().getId());
            ventasQuery.setParameter("anio", meta.getAnio());
            ventasQuery.setParameter("mes", meta.getMes());

            BigDecimal ventasTotales = (BigDecimal) ventasQuery.getSingleResult();

            // Actualizar meta
            meta.setVentasRealizadas(ventasTotales);
            meta.verificarCumplimiento();

            XPersistence.getManager().merge(meta);
            actualizadas++;
        }

        addMessage("Ventas actualizadas para " + actualizadas + " metas");
        tab.reset();
    }
}