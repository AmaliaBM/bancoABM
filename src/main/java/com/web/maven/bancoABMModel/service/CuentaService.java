package com.web.maven.bancoABMModel.service;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.repository.CuentaRepository;

import java.math.BigDecimal;
import java.util.List;

public class CuentaService {

    private final CuentaRepository repo;

    public CuentaService(CuentaRepository repo) {
        this.repo = repo;
    }

    public void crearCuenta(String num, BigDecimal saldoInicial) {
        if (repo.existeCuenta(num)) {
            System.out.println("❌ La cuenta ya existe.");
            return;
        }
        repo.guardarCuenta(new CuentaBancaria(num, saldoInicial));
        System.out.println("✔ Cuenta creada.");
    }

    public CuentaBancaria obtenerCuenta(String num) {
        return repo.obtenerCuenta(num);
    }

    public void actualizarSaldo(String num, BigDecimal saldo) {
        repo.actualizarSaldo(num, saldo);
        System.out.println("✔ Saldo actualizado.");
    }

    public void depositar(CuentaBancaria cuenta, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("Monto debe ser mayor a cero");
        cuenta.setSaldo(cuenta.getSaldo().add(monto));
        repo.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    public void retirar(CuentaBancaria cuenta, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("Monto debe ser mayor a cero");
        if (cuenta.getSaldo().compareTo(monto) < 0)
            throw new Exception("Fondos insuficientes");
        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        repo.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    public void transferir(CuentaBancaria origen, CuentaBancaria destino, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("Monto debe ser mayor a cero");
        if (origen.getSaldo().compareTo(monto) < 0)
            throw new Exception("Fondos insuficientes");

        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        repo.actualizarSaldo(origen.getNumeroCuenta(), origen.getSaldo());
        repo.actualizarSaldo(destino.getNumeroCuenta(), destino.getSaldo());
    }
}
