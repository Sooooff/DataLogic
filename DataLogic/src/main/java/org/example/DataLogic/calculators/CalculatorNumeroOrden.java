package org.example.DataLogic.calculators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculatorNumeroOrden {
    private static final AtomicInteger SEQ = new AtomicInteger(100);
    public static String generarNumero(String prefijo) {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return prefijo + "-" + fecha + "-" + SEQ.getAndIncrement();
    }
}
