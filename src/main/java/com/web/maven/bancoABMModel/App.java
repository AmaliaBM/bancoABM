package com.web.maven.bancoABMModel;

import com.web.maven.bancoABMModel.repository.*;
import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.service.CuentaService;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {

        // === 1. Inicializar repositorios ===
        UsuarioRepository usuarioRepo = new UsuarioRepository();


        PersonaRepository personaRepo = new PersonaRepository();


        CuentaRepository cuentaRepo = new CuentaRepository();

        // Si no tienes cuentas.json aún, se generan desde usuarios
        cuentaRepo.inicializarDesdeUsuarios(usuarioRepo.cargarUsuarios());

        MovimientoRepository movimientoRepo = new MovimientoRepository(cuentaRepo);

        // === 2. Probar lectura inicial del JSON ===
        System.out.println("Total usuarios: " + usuarioRepo.cargarUsuarios().size());

        Usuario u = usuarioRepo.buscarPorId("USR001");

        if (u != null) {
            System.out.println("Usuario encontrado: " + u.getNombre() + " " + u.getApellido()
                    + " - cuenta: " + u.getCuentaBancaria());

            CuentaBancaria c = cuentaRepo.buscarPorNumero(u.getCuentaBancaria());

            if (c != null) {
                System.out.println("Saldo cuenta: " + c.getSaldo());
            } else {
                System.out.println("Cuenta no encontrada para usuario.");
            }
        }

        // === 3. Probar CuentaService ===
        CuentaService cuentaService = new CuentaService();

        CuentaBancaria c1 = new CuentaBancaria("ES0001", new BigDecimal("1000"));
        CuentaBancaria c2 = new CuentaBancaria("ES0002", new BigDecimal("500"));

        System.out.println("\n=== Pruebas de CuentaService ===");

        cuentaService.depositar(c1, new BigDecimal("200"));
        System.out.println("Saldo c1 tras depositar: " + c1.getSaldo());

        cuentaService.retirar(c1, new BigDecimal("50"));
        System.out.println("Saldo c1 tras retirar: " + c1.getSaldo());

        cuentaService.transferir(c1, c2, new BigDecimal("300"));
        System.out.println("Saldo c1 tras transferir: " + c1.getSaldo());
        System.out.println("Saldo c2 tras transferir: " + c2.getSaldo());

        // === 4. Probar carga de movimientos desde JSON ===
        System.out.println("Movimientos cargados: " + movimientoRepo.cargarMovimientos().size());
    }
}
