package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;

public class DepositoBancario extends Movimiento {

    public DepositoBancario(BigDecimal cantidad, CuentaBancaria cuenta) {
        super(cantidad, cuenta, cuenta);
        this.tipo = "Deposito";
    }
}