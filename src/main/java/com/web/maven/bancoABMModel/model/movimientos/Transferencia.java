package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transferencia extends Movimiento {

    public Transferencia(LocalDateTime fecha, BigDecimal cantidad, CuentaBancaria origen, CuentaBancaria destino) {
        super("Transferencia", cantidad, origen, destino, fecha);
    }
}
