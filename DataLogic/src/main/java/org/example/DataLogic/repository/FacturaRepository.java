package org.example.DataLogic.repository;

import org.example.DataLogic.model.Factura;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class FacturaRepository {
    private final ConcurrentHashMap<Long, Factura> store = new ConcurrentHashMap<>();

    public Factura save(Factura f) { store.put(f.getId(), f); return f; }
    public Factura findById(long id) { return store.get(id); }
    public Collection<Factura> findAll() { return store.values(); }
}
