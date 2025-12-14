package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.movimientos.Movimiento;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

public class MovimientoRepository {

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
