package org.example.DataLogic.controllers;

import org.example.DataLogic.model.Empleado;
import org.example.DataLogic.model.MetaVenta;
import org.openxava.actions.ViewBaseAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ReporteCumplimientoController extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        Long empleadoId = (Long) getView().getValue("empleado.id");

        if (empleadoId == null) {
            addError("Debe seleccionar un empleado para generar el reporte");
            return;
        }

        Empleado empleado = XPersistence.getManager().find(Empleado.class, empleadoId);

        // Obtener todas las metas del empleado
        Query query = XPersistence.getManager().createQuery(
                "SELECT m FROM MetaVenta m WHERE m.empleado.id = :empleadoId ORDER BY m.anio DESC, m.mes DESC"
        );
        query.setParameter("empleadoId", empleadoId);

        List<MetaVenta> metas = query.getResultList();

        if (metas.isEmpty()) {
            addError("El empleado " + empleado.getNombreCompleto() + " no tiene metas definidas");
            return;
        }

        // Calcular totales
        BigDecimal totalMeta = BigDecimal.ZERO;
        BigDecimal totalVentas = BigDecimal.ZERO;
        int metasCumplidas = 0;

        for (MetaVenta meta : metas) {
            totalMeta = totalMeta.add(meta.getMontoMeta());
            totalVentas = totalVentas.add(meta.getVentasRealizadas());
            if (Boolean.TRUE.equals(meta.getCumplida())) {
                metasCumplidas++;
            }
        }

        BigDecimal porcentajeGeneral = totalMeta.compareTo(BigDecimal.ZERO) > 0 ?
                totalVentas.multiply(new BigDecimal(100))
                        .divide(totalMeta, 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        // Generar reporte HTML
        StringBuilder reporte = new StringBuilder();
        reporte.append("<div style='padding: 20px; border: 1px solid #ccc; background: #f9f9f9;'>");
        reporte.append("<h2>📊 REPORTE DE CUMPLIMIENTO</h2>");
        reporte.append("<hr>");
        reporte.append("<p><strong>Empleado:</strong> ").append(empleado.getNombreCompleto()).append("</p>");
        reporte.append("<p><strong>Total Metas:</strong> C$").append(totalMeta).append("</p>");
        reporte.append("<p><strong>Total Ventas:</strong> C$").append(totalVentas).append("</p>");
        reporte.append("<p><strong>Porcentaje General:</strong> ").append(porcentajeGeneral).append("%</p>");
        reporte.append("<p><strong>Metas Cumplidas:</strong> ").append(metasCumplidas)
                .append(" de ").append(metas.size()).append("</p>");

        reporte.append("<h3>📅 DETALLE POR MES</h3>");
        reporte.append("<table border='1' cellpadding='8' style='width:100%; border-collapse: collapse;'>");
        reporte.append("<tr style='background: #e0e0e0;'><th>Año/Mes</th><th>Meta</th><th>Ventas</th><th>% Cumplimiento</th><th>Estado</th></tr>");

        for (MetaVenta meta : metas) {
            String estado = meta.getCumplida() ?
                    "<span style='color: green;'>✅ CUMPLIDA</span>" :
                    "<span style='color: red;'>❌ NO CUMPLIDA</span>";

            reporte.append("<tr>")
                    .append("<td>").append(meta.getAnio()).append("/").append(String.format("%02d", meta.getMes())).append("</td>")
                    .append("<td>C$").append(meta.getMontoMeta()).append("</td>")
                    .append("<td>C$").append(meta.getVentasRealizadas()).append("</td>")
                    .append("<td>").append(meta.getPorcentajeCumplimiento()).append("%</td>")
                    .append("<td>").append(estado).append("</td>")
                    .append("</tr>");
        }

        reporte.append("</table>");
        reporte.append("</div>");

        // Mostrar reporte
        addMessage("REPORTE GENERADO EXITOSAMENTE");
        addMessage(reporte.toString());
    }
}