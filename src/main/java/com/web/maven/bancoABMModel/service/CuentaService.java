package com.web.maven.bancoABMModel.service;


import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.repository.CuentaRepository;

import java.math.BigDecimal;
import java.util.List;

public class CuentaService {

    private CuentaRepository repo = new CuentaRepository();

    public void crearCuenta(String num, double saldoInicial) {
        if (repo.existeCuenta(num)) {
            System.out.println("❌ La cuenta ya existe.");
            return;
        }
        repo.guardarCuenta(new CuentaBancaria(num, saldoInicial));
        System.out.println("✔ Cuenta creada.");
    }

    public CuentaBancaria obtenerCuenta(String num) {
        return repo.buscarCuenta(num);
    }

    public List<CuentaBancaria> obtenerTodas() {
        return repo.listarCuentas();
    }

    public void actualizarSaldo(String num, double saldo) {
        repo.actualizarSaldo(num, saldo);
        System.out.println("✔ Saldo actualizado.");
    }

    public void borrarCuenta(String num) {
        repo.eliminarCuenta(num);
        System.out.println("✔ Cuenta eliminada.");
    }

    // === JSON → MySQL ===
    public void importarDesdeJson(List<CuentaBancaria> cuentasJson) {
        for (CuentaBancaria c : cuentasJson) {
            if (!repo.existeCuenta(c.getNumeroCuenta())) {
                repo.guardarCuenta(c);
                System.out.println("Importada cuenta: " + c.getNumeroCuenta());
            }
        }
    }

    public void transferir(CuentaBancaria cuentaUsuario, CuentaBancaria destino, BigDecimal monto) {
    }

    public void retirar(CuentaBancaria cuentaUsuario, BigDecimal monto) {
    }

    public void depositar(CuentaBancaria cuentaUsuario, BigDecimal monto) {

    }
}