package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.DepositoBancario;
import com.web.maven.bancoABMModel.model.movimientos.Retiro;
import com.web.maven.bancoABMModel.model.movimientos.Transferencia;
import com.web.maven.bancoABMModel.model.movimientos.Movimiento;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoRepository {

    // Mantener registro de movimientos en memoria (si quieres)
    private final List<Movimiento> movimientos = new ArrayList<>();

    // Registrar movimiento en BD
    public void registrar(Movimiento m) {

        String sql = """
            INSERT INTO movimientos 
            (tipo, cuenta_origen, cuenta_destino, monto)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getCuentaOrigen().getNumeroCuenta());

            if (m.getCuentaDestino() != null) {
                ps.setString(3, m.getCuentaDestino().getNumeroCuenta());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            ps.setBigDecimal(4, m.getCantidad());

            ps.executeUpdate();

            // Guardar también en memoria
            movimientos.add(m);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtener lista de movimientos por número de cuenta
    public List<Movimiento> obtenerPorCuenta(String numeroCuenta) {
        List<Movimiento> lista = new ArrayList<>();

        String sql = "SELECT * FROM movimientos WHERE cuenta_origen = ? OR cuenta_destino = ? ORDER BY fecha_hora DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ps.setString(2, numeroCuenta);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                BigDecimal monto = rs.getBigDecimal("monto");
                String origenNum = rs.getString("cuenta_origen");
                String destinoNum = rs.getString("cuenta_destino");
                Timestamp fecha = rs.getTimestamp("fecha_hora");

                CuentaBancaria origen = new CuentaBancaria(origenNum, BigDecimal.ZERO); // saldo temporal
                CuentaBancaria destino = destinoNum != null ? new CuentaBancaria(destinoNum, BigDecimal.ZERO) : null;

                Movimiento movimiento;

                switch (tipo) {
                    case "Deposito" -> movimiento = new DepositoBancario(monto, origen);
                    case "Retiro" -> movimiento = new Retiro(monto, origen);
                    case "Transferencia" -> movimiento = new Transferencia(monto, origen, destino);
                    default -> {
                        movimiento = new DepositoBancario(monto, origen); // fallback simple
                    }
                }

                // Ajustar fecha según la BD
                movimiento.setFecha(fecha.toLocalDateTime());

                lista.add(movimiento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
