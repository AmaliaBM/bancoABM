package com.web.maven.bancoABMModel.model.movimientos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.web.maven.bancoABMModel.model.CuentaBancaria;

public class DepositoBancario extends Deposito {

    public DepositoBancario() {
        super();
    }

    public DepositoBancario(LocalDateTime fecha, BigDecimal monto,
                            CuentaBancaria cuentaOrigen, String canal, String origen) {

        super(origen);
        setFecha(fecha);
        setMonto(monto);
        setCuentaOrigen(cuentaOrigen);
        setCanal(canal);
    }

    @Override
    public void procesar() {
        CuentaBancaria cuenta = getCuentaOrigen();
        if (cuenta == null) return;

        cuenta.setSaldo(cuenta.getSaldo().add(getMonto()));
        cuenta.agregarMovimiento(this);
    }
}
