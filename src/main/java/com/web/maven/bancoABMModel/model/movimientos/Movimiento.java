package com.web.maven.bancoABMModel.model.movimientos;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Movimiento {

    protected BigDecimal cantidad;
    protected CuentaBancaria cuentaOrigen;
    protected CuentaBancaria cuentaDestino;
    protected LocalDateTime fecha;
    protected String tipo;

    // Constructor simple (depósitos y retiros)
    public Movimiento(BigDecimal cantidad, CuentaBancaria cuenta) {
        this.cantidad = cantidad;
        this.cuentaOrigen = cuenta;
        this.fecha = LocalDateTime.now();
    }

    // Constructor completo (transferencias)
    public Movimiento(BigDecimal cantidad, CuentaBancaria origen, CuentaBancaria destino) {
        this.cantidad = cantidad;
        this.cuentaOrigen = origen;
        this.cuentaDestino = destino;
        this.fecha = LocalDateTime.now();
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

    public String getTipo() {
        return tipo;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
