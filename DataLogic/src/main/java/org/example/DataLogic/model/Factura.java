package org.example.DataLogic.model;

import java.util.ArrayList;
import java.util.List;

public class Factura extends BaseEntity {
    private String numeroFactura;
    private List<LineItem> items = new ArrayList<>();
    private Long ordenOrigenId;     // si viene de orden
    private Long cotizacionOrigenId;// si viene de cotizacion
    private Long empleadoId;        // el vendedor que hizo la venta (IMPORTANTE PARA REPORTES)

    public Factura(String numeroFactura, Long empleadoId) {
        super();
        this.numeroFactura = numeroFactura;
        this.empleadoId = empleadoId;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public List<LineItem> getItems() { return items; }
    public void addItem(LineItem item) { items.add(item); }
    public double getTotal() { return items.stream().mapToDouble(LineItem::getSubtotal).sum(); }

    public Long getOrdenOrigenId() { return ordenOrigenId; }
    public void setOrdenOrigenId(Long ordenOrigenId) { this.ordenOrigenId = ordenOrigenId; }

    public Long getCotizacionOrigenId() { return cotizacionOrigenId; }
    public void setCotizacionOrigenId(Long cotizacionOrigenId) { this.cotizacionOrigenId = cotizacionOrigenId; }

    public Long getEmpleadoId() { return empleadoId; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura #").append(numeroFactura).append(" (id=").append(getId()).append(") - EmpleadoId=").append(empleadoId).append("\n");
        items.forEach(i -> sb.append("  - ").append(i).append("\n"));
        sb.append("Total: ").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
