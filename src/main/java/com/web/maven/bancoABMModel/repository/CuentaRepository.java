package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository {

    // CREATE
    public void guardarCuenta(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas (numeroCuenta, saldo) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cuenta.getNumeroCuenta());
            ps.setBigDecimal(2, cuenta.getSaldo());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - find by ID
    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new CuentaBancaria(
                        rs.getString("numeroCuenta"),
                        rs.getDouble("saldo")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - list all
    public List<CuentaBancaria> listarCuentas() {
        List<CuentaBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new CuentaBancaria(
                        rs.getString("numeroCuenta"),
                        rs.getDouble("saldo")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // UPDATE
    public void actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoSaldo);
            ps.setString(2, numeroCuenta);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void eliminarCuenta(String numeroCuenta) {
        String sql = "DELETE FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // check if exists
    public boolean existeCuenta(String numeroCuenta) {
        String sql = "SELECT 1 FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Buscar por número de cuenta (devuelve null si no existe)
    public CuentaBancaria buscarPorNumero(String cuentaBancaria) {
        return buscarCuenta(cuentaBancaria);
    }

    // Inicializar cuentas desde JSON de usuarios
    public void inicializarDesdeUsuarios(List<Usuario> usuariosJson) {
        for (Usuario u : usuariosJson) {
            if (u.getCuentaBancaria() != null && !existeCuenta(u.getCuentaBancaria())) {
                guardarCuenta(new CuentaBancaria(u.getCuentaBancaria(), 0));
            }
        }
    }
}