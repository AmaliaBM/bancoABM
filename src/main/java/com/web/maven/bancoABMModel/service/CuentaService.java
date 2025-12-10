package com.web.maven.bancoABMModel.service;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.DepositoBancario;
import com.web.maven.bancoABMModel.model.movimientos.Retiro;
import com.web.maven.bancoABMModel.model.movimientos.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuentaService {

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
    }

    private void validarCuenta(CuentaBancaria cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
    }

    // ===== DEPOSITAR =====
    public void depositar(CuentaBancaria cuenta, BigDecimal monto) {

        validarCuenta(cuenta);
        validarMonto(monto);

        DepositoBancario mov = new DepositoBancario(
                LocalDateTime.now(),
                monto,
                cuenta,
                "deposito",
                "efectivo"
        );

        // Se deja que el movimiento procese la operación
        mov.procesar();
    }

    // ===== RETIRAR =====
    public void retirar(CuentaBancaria cuenta, BigDecimal monto) {

        validarCuenta(cuenta);
        validarMonto(monto);

        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        Retiro mov = new Retiro(
                LocalDateTime.now(),
                monto,
                cuenta,
                "retiro"
        );

        mov.procesar();
    }

    // ===== TRANSFERIR =====
    public void transferir(CuentaBancaria origen, CuentaBancaria destino, BigDecimal monto) {

        validarCuenta(origen);
        validarCuenta(destino);
        validarMonto(monto);

        if (origen.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferir");
        }

        Transferencia mov = new Transferencia(
                LocalDateTime.now(),
                monto,
                origen,
                "transferencia",
                destino
        );

        mov.procesar();
    }
}
