package org.example.DataLogic.repository;

import org.example.DataLogic.model.Empleado;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class EmpleadoRepository {
    private final ConcurrentHashMap<Long, Empleado> store = new ConcurrentHashMap<>();

    public Empleado save(Empleado e) { store.put(e.getId(), e); return e; }
    public Empleado findById(long id) { return store.get(id); }
    public Collection<Empleado> findAll() { return store.values(); }
}
