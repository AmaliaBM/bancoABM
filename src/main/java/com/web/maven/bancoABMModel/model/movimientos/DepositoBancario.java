package com.web.maven.bancoABMModel.model.movimientos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.web.maven.bancoABMModel.model.CuentaBancaria;

public class DepositoBancario extends Movimiento {

    public DepositoBancario() {
        super();
    }

    public DepositoBancario(LocalDateTime fecha, BigDecimal monto,
                            CuentaBancaria cuentaOrigen, String canal) {
        super(fecha, monto, cuentaOrigen, canal);
    }

    @Override
    public void procesar() {
        CuentaBancaria cuenta = getCuentaOrigen();
        if (cuenta == null) return;

        cuenta.setSaldo(cuenta.getSaldo().add(getMonto()));
        cuenta.agregarMovimiento(this);
    }

    @Override
    public String getTipo() {
        return "DEPÓSITO";
    }
}