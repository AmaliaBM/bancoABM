package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;

public class Transferencia extends Movimiento {

    public Transferencia(BigDecimal cantidad,
                         CuentaBancaria origen,
                         CuentaBancaria destino) {

        super(cantidad, origen, destino);
        this.tipo = "Transferencia";
    }
}
