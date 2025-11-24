package org.example.DataLogic.controller;

import org.example.DataLogic.model.Cotizacion;
import org.example.DataLogic.model.LineItem;
import org.example.DataLogic.service.CotizacionService;

import java.util.List;

public class CotizacionController {
    private final CotizacionService service = new CotizacionService();

    public Cotizacion createCotizacion(List<LineItem> items) {
        return service.createCotizacion(items);
    }
}
