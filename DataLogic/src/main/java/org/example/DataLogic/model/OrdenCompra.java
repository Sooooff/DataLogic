package org.example.DataLogic.model;

import java.util.ArrayList;
import java.util.List;

public class OrdenCompra extends BaseEntity {
    private String numeroOrden;
    private List<LineItem> items = new ArrayList<>();
    private Long empleadoId; // quien genera la orden (quien la crea)
    private boolean convertidoAInvoice = false;

    public OrdenCompra(String numeroOrden, Long empleadoId) {
        super();
        this.numeroOrden = numeroOrden;
        this.empleadoId = empleadoId;
    }

    public String getNumeroOrden() { return numeroOrden; }
    public List<LineItem> getItems() { return items; }
    public Long getEmpleadoId() { return empleadoId; }

    public void addItem(LineItem item) { items.add(item); }
    public double getTotal() { return items.stream().mapToDouble(LineItem::getSubtotal).sum(); }
    public boolean isConvertidoAInvoice() { return convertidoAInvoice; }
    public void setConvertidoAInvoice(boolean b) { this.convertidoAInvoice = b; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orden #").append(numeroOrden).append(" (id=").append(getId()).append(") - EmpleadoId=").append(empleadoId).append("\n");
        items.forEach(i -> sb.append("  - ").append(i).append("\n"));
        sb.append("Total: ").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
