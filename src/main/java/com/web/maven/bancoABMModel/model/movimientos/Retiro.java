package com.web.maven.bancoABMModel.model.movimientos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.web.maven.bancoABMModel.model.CuentaBancaria;

public class Retiro extends Movimiento {

    public Retiro() {
        super();
    }

    public Retiro(LocalDateTime fecha, BigDecimal monto, CuentaBancaria cuentaOrigen, String canal) {
        super(fecha, monto, cuentaOrigen, canal);
    }

    @Override
    public void procesar() {
        CuentaBancaria origen = getCuentaOrigen();
        if (origen == null) return;
        origen.setSaldo(origen.getSaldo().subtract(getMonto()));
        origen.agregarMovimiento(this);
    }
}
