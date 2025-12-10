package com.web.maven.bancoABMModel.model.movimientos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.web.maven.bancoABMModel.model.CuentaBancaria;

public class Transferencia extends Movimiento {

    private CuentaBancaria cuentaDestino;

    public Transferencia() {
        super();
    }

    public Transferencia(LocalDateTime fecha, BigDecimal monto, CuentaBancaria cuentaOrigen,
                         String canal, CuentaBancaria cuentaDestino) {
        super(fecha, monto, cuentaOrigen, canal);
        this.cuentaDestino = cuentaDestino;
    }

    public CuentaBancaria getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(CuentaBancaria cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public void procesar() {
        CuentaBancaria origen = getCuentaOrigen();
        CuentaBancaria destino = getCuentaDestino();
        if (origen == null || destino == null) return;

        origen.setSaldo(origen.getSaldo().subtract(getMonto()));
        destino.setSaldo(destino.getSaldo().add(getMonto()));

        origen.agregarMovimiento(this);
        destino.agregarMovimiento(this);
    }
}
