package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.*;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoRepository {

    // Registrar cualquier tipo de movimiento
    public void registrar(Movimiento m) {
        String sql = "INSERT INTO movimientos (tipo, cuenta_origen, cuenta_destino, monto, fecha_hora) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getCuentaOrigen() != null ? m.getCuentaOrigen().getNumeroCuenta() : null);
            ps.setString(3, m.getCuentaDestino() != null ? m.getCuentaDestino().getNumeroCuenta() : null);
            ps.setDouble(4, m.getCantidad().doubleValue());
            ps.setTimestamp(5, Timestamp.valueOf(m.getFecha()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {  // para DatabaseConfig.getConnection() si lanza Exception
            e.printStackTrace();
        }
    }

    public void importarDesdeJson(List<Movimiento> movimientos) {
        if (movimientos == null) return;
        for (Movimiento m : movimientos) registrar(m);
    }

    public List<Movimiento> obtenerMovimientos(String numeroCuenta) {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos WHERE cuenta_origen = ? OR cuenta_destino = ? ORDER BY fecha_hora DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ps.setString(2, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String origen = rs.getString("cuenta_origen");
                String destino = rs.getString("cuenta_destino");
                BigDecimal monto = BigDecimal.valueOf(rs.getDouble("monto"));
                LocalDateTime fecha = rs.getTimestamp("fecha_hora").toLocalDateTime();

                Movimiento m = null;
                switch (tipo) {
                    case "Deposito":
                        m = new DepositoBancario(fecha, monto, new CuentaBancaria(origen, 0));
                        break;
                    case "Retiro":
                        m = new Retiro(fecha, monto, new CuentaBancaria(origen, 0));
                        break;
                    case "Transferencia":
                        m = new Transferencia(fecha, monto, new CuentaBancaria(origen, 0), new CuentaBancaria(destino, 0));
                        break;
                }
                if (m != null) lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
