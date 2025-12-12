package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.util.JsonReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    private final List<Usuario> usuarios;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>(JsonReader.cargarUsuariosPolimorficos("data/usuarios.json"));
    }

    public Usuario buscarPorId(String idUsuario) {
        if (idUsuario == null) return null;
        Optional<Usuario> opt = usuarios.stream()
                .filter(u -> idUsuario.equalsIgnoreCase(u.getIdUsuario()))
                .findFirst();
        return opt.orElse(null);
    }

    public Usuario buscarPorDni(String dni) {
        if (dni == null) return null;
        Optional<Usuario> opt = usuarios.stream()
                .filter(u -> dni.equalsIgnoreCase(u.getDni()))
                .findFirst();
        return opt.orElse(null);
    }

    public void guardar(Usuario u) {
        usuarios.add(u);
    }

    public void importarDesdeJson(List<Usuario> usuariosJson) {
        if (usuariosJson == null) return;
        usuarios.addAll(usuariosJson);
    }
}
