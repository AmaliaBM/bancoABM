package com.web.maven.bancoABMModel;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.model.movimientos.*;
import com.web.maven.bancoABMModel.repository.*;
import com.web.maven.bancoABMModel.service.CuentaService;
import com.web.maven.bancoABMModel.util.JsonLoader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppConsola {

    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final CuentaRepository cuentaRepo = new CuentaRepository();
    private static final MovimientoRepository movimientoRepo = new MovimientoRepository();
    private static final CuentaService cuentaService = new CuentaService();

    private static final Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual;

    public static void main(String[] args) {

        // =========================
        // CARGAR DATOS DESDE JSON
        // =========================
        System.out.println("=== CARGANDO DATOS DESDE JSON ===");
        new JsonLoader(usuarioRepo, cuentaRepo, movimientoRepo).cargarTodo();

        // =========================
        // LOGIN
        // =========================
        System.out.println("=== BIENVENIDO AL BANCO ===");
        login();

        // =========================
        // MENU PRINCIPAL
        // =========================
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1 -> verSaldo();
                case 2 -> depositar();
                case 3 -> retirar();
                case 4 -> transferir();
                case 5 -> verMovimientos();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void login() {
        System.out.print("Ingrese su DNI de usuario: ");
        String dni = scanner.nextLine();
        usuarioActual = usuarioRepo.buscarPorDni(dni);
        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado. Saliendo...");
            System.exit(0);
        }
        System.out.println("¡Hola, " + usuarioActual.getNombreCompleto() + "!");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ ===");
        System.out.println("1. Ver saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Retirar");
        System.out.println("4. Transferir");
        System.out.println("5. Ver movimientos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static CuentaBancaria getCuentaUsuario() {
        return cuentaRepo.obtenerCuenta(usuarioActual.getCuentaBancaria());
    }

    private static void verSaldo() {
        CuentaBancaria c = getCuentaUsuario();
        if (c != null) {
            System.out.println("Saldo actual: " + c.getSaldo());
        } else {
            System.out.println("No se encontró la cuenta.");
        }
    }

    private static void depositar() {
        try {
            System.out.print("Monto a depositar: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());
            CuentaBancaria c = getCuentaUsuario();
            cuentaService.depositar(c, monto);

            Movimiento m = new DepositoBancario(LocalDateTime.now(), monto, c);
            movimientoRepo.registrar(m);

            System.out.println("Depósito exitoso.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void retirar() {
        try {
            System.out.print("Monto a retirar: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());
            CuentaBancaria c = getCuentaUsuario();
            cuentaService.retirar(c, monto);

            Movimiento m = new Retiro(LocalDateTime.now(), monto, c);
            movimientoRepo.registrar(m);

            System.out.println("Retiro exitoso.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transferir() {
        try {
            System.out.print("Número de cuenta destino: ");
            String numDestino = scanner.nextLine();

            CuentaBancaria origen = getCuentaUsuario();
            CuentaBancaria destino = cuentaRepo.obtenerCuenta(numDestino);

            if (destino == null) {
                System.out.println("Cuenta destino no encontrada.");
                return;
            }

            System.out.print("Monto a transferir: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());

            cuentaService.transferir(origen, destino, monto);

            Movimiento m = new Transferencia(LocalDateTime.now(), monto, origen, destino);
            movimientoRepo.registrar(m);

            System.out.println("Transferencia exitosa.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verMovimientos() {
        CuentaBancaria c = getCuentaUsuario();
        if (c == null) {
            System.out.println("No se encontró la cuenta.");
            return;
        }

        List<Movimiento> movs = movimientoRepo.obtenerMovimientos(c.getNumeroCuenta());
        System.out.println("=== MOVIMIENTOS ===");
        for (Movimiento m : movs) {
            System.out.println(m.getTipo() + " | " + m.getFecha() + " | " + m.getCantidad());
        }
    }
}
