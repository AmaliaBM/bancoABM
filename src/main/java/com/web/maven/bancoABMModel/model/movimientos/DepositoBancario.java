package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositoBancario extends Movimiento {

    // Constructor para crear desde la app (fecha = ahora)
    public DepositoBancario(CuentaBancaria cuenta, BigDecimal cantidad) {
        super("Deposito", cantidad, cuenta, null, LocalDateTime.now());
    }

    // Constructor para crear desde DB (fecha conocida)
    public DepositoBancario(LocalDateTime fecha, BigDecimal cantidad, CuentaBancaria cuenta) {
        super("Deposito", cantidad, cuenta, null, fecha);
    }
}

