package org.example.DataLogic.controller;

import org.example.DataLogic.model.LineItem;
import org.example.DataLogic.model.OrdenCompra;
import org.example.DataLogic.service.OrdenService;

import java.util.List;

public class OrdenController {
    private final OrdenService service = new OrdenService();

    public OrdenCompra createOrden(List<LineItem> items, Long empleadoId) {
        return service.createOrden(items, empleadoId);
    }

    public OrdenCompra findById(long id) { return service.findById(id); }
}
