package org.example.DataLogic.repository;

import org.example.DataLogic.model.OrdenCompra;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class OrdenRepository {
    private final ConcurrentHashMap<Long, OrdenCompra> store = new ConcurrentHashMap<>();

    public OrdenCompra save(OrdenCompra o) { store.put(o.getId(), o); return o; }
    public OrdenCompra findById(long id) { return store.get(id); }
    public Collection<OrdenCompra> findAll() { return store.values(); }
}
