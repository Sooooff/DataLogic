package org.example.DataLogic.repository;

import org.example.DataLogic.model.Cotizacion;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class CotizacionRepository {
    private final ConcurrentHashMap<Long, Cotizacion> store = new ConcurrentHashMap<>();

    public Cotizacion save(Cotizacion c) { store.put(c.getId(), c); return c; }
    public Cotizacion findById(long id) { return store.get(id); }
    public Collection<Cotizacion> findAll() { return store.values(); }
}
