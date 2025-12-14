package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();

    public void agregarUsuario(Usuario u) {
        if (!usuarios.contains(u)) {
            usuarios.add(u);
        }



    }

    public void guardarEnBD(Usuario u) {
        if (!usuarios.contains(u)) {
            usuarios.add(u);
        }

        // Genera idUsuario único si quieres usarlo como identificador único visible
        if (u.getIdUsuario() == null || u.getIdUsuario().isEmpty()) {
            u.setIdUsuario("U" + System.currentTimeMillis());
        }

        String sql = """
        INSERT INTO usuario (idUsuario, dni, nombre, apellido, cuentaBancaria, hashContrasenya, tipo)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getIdUsuario());
            ps.setString(2, u.getDni());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getApellido());
            ps.setString(5, u.getCuentaBancaria());
            ps.setString(6, u.getHashContrasenya());
            ps.setString(7, u.getTipo());

            ps.executeUpdate();

            // Obtener ID autogenerado por la base de datos y actualizar el objeto Usuario
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1)); // Este es el campo id AUTO_INCREMENT
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Usuario buscarPorDni(String dni) {
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni)) return u;
        }

        String sql = "SELECT * FROM usuario WHERE dni = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getString("idUsuario"));
                u.setDni(rs.getString("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCuentaBancaria(rs.getString("cuentaBancaria"));
                u.setHashContrasenya(rs.getString("hashContrasenya"));
                usuarios.add(u);
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
