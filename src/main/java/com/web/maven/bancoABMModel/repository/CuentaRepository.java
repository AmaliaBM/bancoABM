package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository {

    public boolean existeCuenta(String numeroCuenta) {
        String sql = "SELECT COUNT(*) FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void guardarCuenta(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas (numeroCuenta, saldo) VALUES (?, ?) ON DUPLICATE KEY UPDATE saldo=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cuenta.getNumeroCuenta());
            ps.setDouble(2, cuenta.getSaldo().doubleValue());
            ps.setDouble(3, cuenta.getSaldo().doubleValue());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CuentaBancaria obtenerCuenta(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CuentaBancaria(rs.getString("numeroCuenta"), rs.getDouble("saldo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CuentaBancaria> listarCuentas() {
        List<CuentaBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new CuentaBancaria(rs.getString("numeroCuenta"), rs.getDouble("saldo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarSaldo(String numeroCuenta, double saldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, saldo);
            ps.setString(2, numeroCuenta);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void importarDesdeJson(List<CuentaBancaria> cuentas) {
        if (cuentas == null) return;
        for (CuentaBancaria c : cuentas) guardarCuenta(c);
    }
}
