package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Movimiento {

    protected String tipo;
    protected BigDecimal cantidad;
    protected CuentaBancaria cuentaOrigen;
    protected CuentaBancaria cuentaDestino;
    protected LocalDateTime fecha;

    public Movimiento(String tipo, BigDecimal cantidad, CuentaBancaria origen, CuentaBancaria destino, LocalDateTime fecha) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.cuentaOrigen = origen;
        this.cuentaDestino = destino;
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public CuentaBancaria getCuentaOrigen() {
        return cuentaOrigen;
    }

    public CuentaBancaria getCuentaDestino() {
        return cuentaDestino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public void setCuentaOrigen(CuentaBancaria cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaDestino(CuentaBancaria cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
