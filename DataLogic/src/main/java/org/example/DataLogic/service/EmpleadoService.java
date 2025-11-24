package org.example.DataLogic.service;

import org.example.DataLogic.model.Empleado;
import org.example.DataLogic.repository.EmpleadoRepository;

public class EmpleadoService {
    private final EmpleadoRepository repo = new EmpleadoRepository();

    public Empleado createEmpleado(String cif, String nombre, String rol) {
        Empleado e = new Empleado(cif, nombre, rol);
        repo.save(e);
        return e;
    }

    public Empleado findById(long id) { return repo.findById(id); }

    public Empleado save(Empleado e) { return repo.save(e); }
}
