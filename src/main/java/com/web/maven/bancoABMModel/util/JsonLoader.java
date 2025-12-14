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
        for (Usuario u : usuarios) {
            // Solo guarda en memoria para login desde JSON
            usuarioRepo.agregarUsuario(u);
        }

        // === 2) CUENTAS ===
        List<CuentaBancaria> cuentas = JsonReader.cargarLista("data/cuentas.json", CuentaBancaria[].class);
        for (CuentaBancaria c : cuentas) {
            // Guarda en memoria y BD si no existe
            if (cuentaRepo.obtenerCuenta(c.getNumeroCuenta()) == null) {
                cuentaRepo.guardarCuenta(c);
            }
        }

        // === 3) MOVIMIENTOS ===
        List<Movimiento> movimientos = JsonReader.cargarLista("data/movimientos.json", Movimiento[].class);
        for (Movimiento m : movimientos) {
            movimientoRepo.registrar(m);
        }

        System.out.println("=== Datos cargados desde JSON correctamente ===");
    }
}
