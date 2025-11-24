package org.example.DataLogic.model;

public class Producto extends BaseEntity {
    private String codigo;
    private String nombre;
    private double precioUnitario;

    public Producto(String codigo, String nombre, double precioUnitario) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioUnitario() { return precioUnitario; }

    @Override
    public String toString() {
        return String.format("%s - %s (%.2f)", codigo, nombre, precioUnitario);
    }
}
