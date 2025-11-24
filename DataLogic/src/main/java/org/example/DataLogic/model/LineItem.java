package org.example.DataLogic.model;

public class LineItem {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineItem(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioUnitario();
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return cantidad * precioUnitario; }

    @Override
    public String toString() {
        return String.format("%s x%d = %.2f", producto.getNombre(), cantidad, getSubtotal());
    }
}
