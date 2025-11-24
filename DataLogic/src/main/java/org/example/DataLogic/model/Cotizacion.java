package org.example.DataLogic.model;

import java.util.ArrayList;
import java.util.List;

public class Cotizacion extends BaseEntity {
    private String numeroCotizacion;
    private List<LineItem> items = new ArrayList<>();

    public Cotizacion(String numeroCotizacion) {
        super();
        this.numeroCotizacion = numeroCotizacion;
    }

    public String getNumeroCotizacion() { return numeroCotizacion; }
    public List<LineItem> getItems() { return items; }
    public void addItem(LineItem item) { items.add(item); }
    public double getTotal() { return items.stream().mapToDouble(LineItem::getSubtotal).sum(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cotizacion #").append(numeroCotizacion).append(" (id=").append(getId()).append(")\n");
        items.forEach(i -> sb.append("  - ").append(i).append("\n"));
        sb.append("Total: ").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
