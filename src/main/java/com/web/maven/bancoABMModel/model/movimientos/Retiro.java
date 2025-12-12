package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Retiro extends Movimiento {

    public Retiro(LocalDateTime fecha, BigDecimal cantidad, CuentaBancaria cuentaOrigen) {
        super("Retiro", cantidad, cuentaOrigen, null, fecha);
    }
}
