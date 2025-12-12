package com.web.maven.bancoABMModel.service;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.repository.CuentaRepository;

import java.math.BigDecimal;
import java.util.List;

public class CuentaService {

    private CuentaRepository repo = new CuentaRepository();

    // Crear cuenta
    public void crearCuenta(String num, double saldoInicial) {
        if (repo.existeCuenta(num)) {
            System.out.println("❌ La cuenta ya existe.");
            return;
        }
        repo.guardarCuenta(new CuentaBancaria(num, saldoInicial));
        System.out.println("✔ Cuenta creada.");
    }

    // Obtener cuenta
    public CuentaBancaria obtenerCuenta(String num) {
        return repo.obtenerCuenta(num);
    }

    // Listar todas
    public List<CuentaBancaria> obtenerTodas() {
        return repo.listarCuentas();
    }

    // Actualizar saldo
    public void actualizarSaldo(String num, double saldo) {
        repo.actualizarSaldo(num, saldo);
        System.out.println("✔ Saldo actualizado.");
    }

    // Borrar cuenta
    public void borrarCuenta(String num) {
        repo.eliminarCuenta(num);
        System.out.println("✔ Cuenta eliminada.");
    }

    // Importar desde JSON
    public void importarDesdeJson(List<CuentaBancaria> cuentasJson) {
        for (CuentaBancaria c : cuentasJson) {
            if (!repo.existeCuenta(c.getNumeroCuenta())) {
                repo.guardarCuenta(c);
                System.out.println("Importada cuenta: " + c.getNumeroCuenta());
            }
        }
    }

    // ==========================
    // OPERACIONES DE SALDO
    // ==========================
    public void depositar(CuentaBancaria cuentaUsuario, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El monto a depositar debe ser mayor que cero.");
        }
        BigDecimal nuevoSaldo = cuentaUsuario.getSaldo().add(monto);
        cuentaUsuario.setSaldo(nuevoSaldo);
        repo.actualizarSaldo(cuentaUsuario.getNumeroCuenta(), nuevoSaldo.doubleValue());
    }

    public void retirar(CuentaBancaria cuentaUsuario, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El monto a retirar debe ser mayor que cero.");
        }
        if (cuentaUsuario.getSaldo().compareTo(monto) < 0) {
            throw new Exception("Fondos insuficientes.");
        }
        BigDecimal nuevoSaldo = cuentaUsuario.getSaldo().subtract(monto);
        cuentaUsuario.setSaldo(nuevoSaldo);
        repo.actualizarSaldo(cuentaUsuario.getNumeroCuenta(), nuevoSaldo.doubleValue());
    }

    public void transferir(CuentaBancaria cuentaUsuario, CuentaBancaria destino, BigDecimal monto) throws Exception {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El monto a transferir debe ser mayor que cero.");
        }
        if (cuentaUsuario.getSaldo().compareTo(monto) < 0) {
            throw new Exception("Fondos insuficientes para la transferencia.");
        }

        // Actualizar saldos
        cuentaUsuario.setSaldo(cuentaUsuario.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        // Actualizar en base de datos
        repo.actualizarSaldo(cuentaUsuario.getNumeroCuenta(), cuentaUsuario.getSaldo().doubleValue());
        repo.actualizarSaldo(destino.getNumeroCuenta(), destino.getSaldo().doubleValue());
    }
}
