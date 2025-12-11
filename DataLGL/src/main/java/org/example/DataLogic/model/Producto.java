package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@Table(name = "producto")
@Views({
        @View(name = "Simple", members = "nombre, precioPorUnidad, cantidadEnInventario"),
        @View(name = "Completo", members = "nombre, descripcion, precioPorUnidad, precioPorMayor, cantidadEnInventario, precioCalculadoPorUnidad")
})
public class Producto extends BaseEntity {

    @Column(length = 100, nullable = false)
    @Required
    @DisplaySize(30)
    private String nombre;

    @Column(length = 255)
    @TextArea
    private String descripcion;

    @Column(precision = 10, scale = 2, nullable = false)
    @Required
    @Money
    private BigDecimal precioPorUnidad;

    @Column(precision = 10, scale = 2, nullable = false)
    @Required
    @Money
    private BigDecimal precioPorMayor;

    @Column(nullable = false)
    @Required
    private Integer cantidadEnInventario = 0;

    @Column(precision = 10, scale = 2)
    @ReadOnly
    @Money
    @Depends("precioPorMayor, cantidadEnInventario")
    public BigDecimal getPrecioCalculadoPorUnidad() {
        if (cantidadEnInventario == null || cantidadEnInventario <= 0 ||
                precioPorMayor == null || precioPorMayor.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return precioPorMayor.divide(new BigDecimal(cantidadEnInventario), 2, BigDecimal.ROUND_HALF_UP);
    }

    @PrePersist
    @PreUpdate
    public void calcularPrecioAutomatico() {
        if (precioPorUnidad == null || precioPorUnidad.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal precioCalculado = getPrecioCalculadoPorUnidad();
            if (precioCalculado.compareTo(BigDecimal.ZERO) > 0) {
                precioPorUnidad = precioCalculado;
            }
        }
    }
}