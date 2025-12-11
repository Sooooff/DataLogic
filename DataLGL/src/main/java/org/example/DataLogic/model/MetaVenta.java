package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@Table(name = "meta_venta",
        uniqueConstraints = @UniqueConstraint(columnNames = {"empleado_id", "anio", "mes"}))
@Views({
        @View(name = "Simple", members = "empleado; anio, mes; montoMeta, ventasRealizadas, cumplida"),
        @View(name = "ConReporte", members = "empleado; anio, mes; montoMeta; ventasRealizadas; porcentajeCumplimiento; cumplida")
})
@Tabs({
        @Tab(name = "Todas",
                properties = "empleado.nombreCompleto, anio, mes, montoMeta, ventasRealizadas, porcentajeCumplimiento, cumplida",
                defaultOrder = "${anio} desc, ${mes} desc"),
        @Tab(name = "Cumplidas",
                properties = "empleado.nombreCompleto, anio, mes, montoMeta, ventasRealizadas, porcentajeCumplimiento",
                baseCondition = "${cumplida} = true"),
        @Tab(name = "NoCumplidas",
                properties = "empleado.nombreCompleto, anio, mes, montoMeta, ventasRealizadas, porcentajeCumplimiento",
                baseCondition = "${cumplida} = false")
})
public class MetaVenta extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombreCompleto")
    @Required
    private Empleado empleado;

    @Column(nullable = false)
    @Required
    private int anio;

    @Column(nullable = false)
    @Required
    private int mes;

    @Column(precision = 10, scale = 2, nullable = false)
    @Required
    @Money
    private BigDecimal montoMeta;

    @Column(precision = 10, scale = 2)
    @ReadOnly
    @Money
    private BigDecimal ventasRealizadas = BigDecimal.ZERO;

    @ReadOnly
    private Boolean cumplida = false;

    @Column(precision = 5, scale = 2)
    @ReadOnly
    @Stereotype("PORCENTAJE")
    @Depends("montoMeta, ventasRealizadas")
    public BigDecimal getPorcentajeCumplimiento() {
        if (montoMeta == null || montoMeta.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (ventasRealizadas == null) {
            return BigDecimal.ZERO;
        }
        return ventasRealizadas.multiply(new BigDecimal(100))
                .divide(montoMeta, 2, BigDecimal.ROUND_HALF_UP);
    }

    @PreUpdate
    @PrePersist
    public void verificarCumplimiento() {
        if (ventasRealizadas != null && montoMeta != null) {
            cumplida = ventasRealizadas.compareTo(montoMeta) >= 0;
        } else {
            cumplida = false;
        }
    }
}