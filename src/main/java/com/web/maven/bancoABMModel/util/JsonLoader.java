package com.web.maven.bancoABMModel.util;

import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.Movimiento;
import com.web.maven.bancoABMModel.repository.UsuarioRepository;
import com.web.maven.bancoABMModel.repository.CuentaRepository;
import com.web.maven.bancoABMModel.repository.MovimientoRepository;

import java.util.List;

public class JsonLoader {

    private final UsuarioRepository usuarioRepo;
    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movimientoRepo;

    public JsonLoader(
            UsuarioRepository usuarioRepo,
            CuentaRepository cuentaRepo,
            MovimientoRepository movimientoRepo
    ) {
        this.usuarioRepo = usuarioRepo;
        this.cuentaRepo = cuentaRepo;
        this.movimientoRepo = movimientoRepo;
    }

    public void cargarTodo() {

        // === 1) USUARIOS ===
        List<Usuario> usuarios = JsonReader.cargarLista("data/usuarios.json", Usuario[].class);
        usuarioRepo.importarDesdeJson(usuarios);

        // === 2) CUENTAS ===
        List<CuentaBancaria> cuentas = JsonReader.cargarLista("data/cuentas.json", CuentaBancaria[].class);
        cuentaRepo.importarDesdeJson(cuentas);

        // === 3) MOVIMIENTOS ===
        List<Movimiento> movimientos = JsonReader.cargarLista("data/movimientos.json", Movimiento[].class);
        movimientoRepo.importarDesdeJson(movimientos);

        System.out.println("=== Datos cargados desde JSON correctamente ===");
    }
}
