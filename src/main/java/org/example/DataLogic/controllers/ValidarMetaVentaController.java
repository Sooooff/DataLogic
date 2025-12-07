package org.example.DataLogic.controllers;

import org.example.DataLogic.model.MetaVenta;
import org.openxava.actions.SaveAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.Query;
import java.math.BigDecimal;

public class ValidarMetaVentaController extends SaveAction {

    @Override
    public void execute() throws Exception {

        MetaVenta meta = (MetaVenta) getView().getEntity();

        // Verificar si ya existe una meta para este empleado en el mismo año/mes
        Query query = XPersistence.getManager().createQuery(
                "SELECT COUNT(m) FROM MetaVenta m WHERE " +
                        "m.empleado.id = :empleadoId AND m.anio = :anio AND m.mes = :mes " +
                        "AND (m.id != :id OR :id = 0)"
        );

        query.setParameter("empleadoId", meta.getEmpleado().getId());
        query.setParameter("anio", meta.getAnio());
        query.setParameter("mes", meta.getMes());
        query.setParameter("id", meta.getId() != null ? meta.getId() : 0L);

        Long count = (Long) query.getSingleResult();

        if (count > 0) {
            addError("Ya existe una meta definida para este vendedor en el año " +
                    meta.getAnio() + " mes " + meta.getMes());
            return;
        }

        // Verificar que el monto sea positivo
        if (meta.getMontoMeta().compareTo(BigDecimal.ZERO) <= 0) {
            addError("El monto de la meta debe ser mayor a cero");
            return;
        }

        // Si todo está bien, proceder con el guardado
        super.execute();

        // Mensaje de confirmación
        addMessage("Meta registrada exitosamente para " +
                meta.getEmpleado().getNombre() +
                " - " + meta.getAnio() + "/" + meta.getMes());
    }
}