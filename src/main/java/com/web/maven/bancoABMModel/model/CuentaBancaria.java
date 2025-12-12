package com.web.maven.bancoABMModel.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.web.maven.bancoABMModel.model.movimientos.Movimiento;

public class CuentaBancaria {

    private String numeroCuenta;
    private BigDecimal saldo;
    private List<Movimiento> movimientos;

    public CuentaBancaria() {
        this.saldo = BigDecimal.ZERO;
        this.movimientos = new ArrayList<>();
    }

    public CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
        this.movimientos = new ArrayList<>();
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public void agregarMovimiento(Movimiento movimiento) {
        if (this.movimientos == null) {
            this.movimientos = new ArrayList<>();
        }
        this.movimientos.add(movimiento);
    }
}
