package org.example.DataLogic.model;

import java.time.LocalDate;

public class MetaVenta extends BaseEntity {
    private Long empleadoId;
    private double montoMeta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public MetaVenta(Long empleadoId, double montoMeta, LocalDate fechaInicio, LocalDate fechaFin) {
        super();
        this.empleadoId = empleadoId;
        this.montoMeta = montoMeta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getEmpleadoId() { return empleadoId; }
    public double getMontoMeta() { return montoMeta; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
}
