package org.example.DataLogic.service;

import org.example.DataLogic.model.MetaVenta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio simple que guarda metas en memoria.
 * Para la fase 1 almacenamos en lista simple (puedes mejorar a repo).
 */
public class MetaService {
    private final List<MetaVenta> metas = new ArrayList<>();

    public MetaVenta crearMeta(Long empleadoId, double montoMeta, LocalDate inicio, LocalDate fin) {
        MetaVenta m = new MetaVenta(empleadoId, montoMeta, inicio, fin);
        metas.add(m);
        return m;
    }

    public List<MetaVenta> findMetasPorEmpleado(Long empleadoId) {
        List<MetaVenta> out = new ArrayList<>();
        for (MetaVenta m : metas) {
            if (m.getEmpleadoId().equals(empleadoId)) out.add(m);
        }
        return out;
    }
}
