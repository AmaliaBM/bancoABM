package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class CuentaRepository {

    public void crearCuenta(String numeroCuenta) {
        String sql = "INSERT INTO cuentas (numeroCuenta, saldo) VALUES (?, 0.00)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarSaldo(String numeroCuenta, BigDecimal nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE numeroCuenta = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, nuevoSaldo);
            ps.setString(2, numeroCuenta);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CuentaBancaria obtenerCuenta(String numeroCuenta) {
        String sql = "SELECT numeroCuenta, saldo FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String num = rs.getString("numeroCuenta");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                return new CuentaBancaria(num, saldo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarNumeroCuenta() {
        Random r = new Random();
        return "ES" + (10000 + r.nextInt(90000));
    }

    public boolean existeCuenta(String numeroCuenta) {
        String sql = "SELECT COUNT(*) FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
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

}
