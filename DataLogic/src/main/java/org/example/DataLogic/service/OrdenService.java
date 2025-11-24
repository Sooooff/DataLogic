package org.example.DataLogic.service;

import org.example.DataLogic.calculators.CalculatorNumeroOrden;
import org.example.DataLogic.model.LineItem;
import org.example.DataLogic.model.OrdenCompra;
import org.example.DataLogic.repository.OrdenRepository;

import java.util.List;

public class OrdenService {
    private final OrdenRepository repo = new OrdenRepository();

    public OrdenCompra createOrden(List<LineItem> items, Long empleadoId) {
        String numero = CalculatorNumeroOrden.generarNumero("ORD");
        OrdenCompra o = new OrdenCompra(numero, empleadoId);
        items.forEach(o::addItem);
        repo.save(o);
        return o;
    }

    public OrdenCompra findById(long id) { return repo.findById(id); }
}
