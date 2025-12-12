package com.web.maven.bancoABMModel.model.movimientos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.web.maven.bancoABMModel.model.CuentaBancaria;

public abstract class Movimiento {

    private LocalDateTime fecha;
    private BigDecimal monto;
    private CuentaBancaria cuentaOrigen;
    private String canal;

    public Movimiento() {
    }

    public Movimiento(LocalDateTime fecha, BigDecimal monto, CuentaBancaria cuentaOrigen, String canal) {
        this.fecha = fecha;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.canal = canal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public CuentaBancaria getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(CuentaBancaria cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public abstract void procesar();

    public boolean getTipo() {
    }

    public Object getCantidad() {
    }
}
