package org.example.DataLogic.model;

public class Empleado extends BaseEntity {
    private String cif; // CI o código (ej. 23020138)
    private String nombre;
    private String rol; // texto simple (ej. "Vendedor", "Gerente")
    private Double metaVenta; // meta en monto monetario (puede ser null si no tiene meta)

    public Empleado(String cif, String nombre, String rol) {
        super();
        this.cif = cif;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getCif() { return cif; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public Double getMetaVenta() { return metaVenta; }
    public void setMetaVenta(Double metaVenta) { this.metaVenta = metaVenta; }

    @Override
    public String toString() {
        return String.format("%s (%s) - Rol: %s - Meta: %s", nombre, cif, rol, metaVenta == null ? "No asignada" : String.format("%.2f", metaVenta));
    }
}