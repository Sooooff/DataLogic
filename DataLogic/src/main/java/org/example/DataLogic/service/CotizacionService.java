package org.example.DataLogic.service;

import org.example.DataLogic.calculators.CalculatorNumeroOrden;
import org.example.DataLogic.model.Cotizacion;
import org.example.DataLogic.model.LineItem;
import org.example.DataLogic.repository.CotizacionRepository;

import java.util.List;

public class CotizacionService {
    private final CotizacionRepository repo = new CotizacionRepository();

    public Cotizacion createCotizacion(List<LineItem> items) {
        String numero = CalculatorNumeroOrden.generarNumero("COT");
        Cotizacion c = new Cotizacion(numero);
        items.forEach(c::addItem);
        repo.save(c);
        return c;
    }

    public Cotizacion findById(long id) { return repo.findById(id); }
}
