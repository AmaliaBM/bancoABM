package com.web.maven.bancoABMModel.model.movimientos;

// Clase abstracta para depósitos, si quieres agrupar tipos de depósitos
public abstract class Deposito extends Movimiento {

    public Deposito(String tipo, java.math.BigDecimal cantidad, com.web.maven.bancoABMModel.model.CuentaBancaria cuentaDestino, java.time.LocalDateTime fecha) {
        super(tipo, cantidad, null, cuentaDestino, fecha);
    }
}
