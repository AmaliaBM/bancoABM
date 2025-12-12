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
            ps.setDouble(2, cuenta.getSaldo());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - find by ID
    public Cuenta buscarCuenta(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cuenta(
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
    public List<Cuenta> listarCuentas() {
        List<Cuenta> lista = new ArrayList<>();
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
        String sql = "SELECT COUNT(*) FROM cuentas WHERE numeroCuenta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public void inicializarDesdeUsuarios(List<Usuario> usuariosJson) {
    }

    public CuentaBancaria buscarPorNumero(String cuentaBancaria) {
    }
}