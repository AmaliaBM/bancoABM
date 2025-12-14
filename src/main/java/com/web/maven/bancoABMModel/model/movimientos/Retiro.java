package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;

public class Retiro extends Movimiento {

    public Retiro(BigDecimal cantidad, CuentaBancaria cuentaOrigen) {
        super(cantidad, cuentaOrigen);
        this.tipo = "Retiro";

        // Para cumplir la BD: origen NO puede ser null
        this.cuentaDestino = null;
    }
}
