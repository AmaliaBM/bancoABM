package com.web.maven.bancoABMModel;


import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.Movimiento;
import com.web.maven.bancoABMModel.model.Usuario;

import com.web.maven.bancoABMModel.repository.CuentaRepository;
import com.web.maven.bancoABMModel.repository.MovimientoRepository;
import com.web.maven.bancoABMModel.repository.UsuarioRepository;

import com.web.maven.bancoABMModel.service.CuentaService;
import com.web.maven.bancoABMModel.util.JsonReader;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class AppConsola {

    private static UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static CuentaRepository cuentaRepo = new CuentaRepository();
    private static MovimientoRepository movimientoRepo = new MovimientoRepository();
    private static CuentaService cuentaService = new CuentaService();

    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual;

    public static void main(String[] args) {

        System.out.println("=== CARGANDO DATOS DESDE JSON ===");

        // ===INCIALIZAR CUENTAS Y MOVIMIENTOS importar usuarios y cuentas desde JSON a BD ===
        List<Usuario> usuariosJson = JsonReader.cargarLista("data/usuarios.json", Usuario[].class);
        usuarioRepo.importarDesdeJson(usuariosJson);
        cuentaRepo.inicializarDesdeUsuarios(usuariosJson);

        System.out.println("=== BIENVENIDO AL BANCO ===");
        login();

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
                case 0 -> System.out.println("¡Gracias por usar el banco!");
                default -> System.out.println("Opción inválida");
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
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Ver saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Retirar");
        System.out.println("4. Transferir");
        System.out.println("5. Ver movimientos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static CuentaBancaria getCuentaUsuario() {
        return cuentaRepo.buscarPorNumero(usuarioActual.getCuentaBancaria());
    }

    private static void verSaldo() {
        CuentaBancaria c = getCuentaUsuario();
        System.out.println("Saldo actual: " + c.getSaldo());
    }

    private static void depositar() {
        try {
            System.out.print("Monto a depositar: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());
            cuentaService.depositar(getCuentaUsuario(), monto);
            System.out.println("Depósito exitoso.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void retirar() {
        try {
            System.out.print("Monto a retirar: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());
            cuentaService.retirar(getCuentaUsuario(), monto);
            System.out.println("Retiro exitoso.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transferir() {
        try {
            System.out.print("Número de cuenta destino: ");
            String numeroDestino = scanner.nextLine();
            CuentaBancaria destino = cuentaRepo.buscarPorNumero(numeroDestino);

            if (destino == null) {
                System.out.println("Cuenta destino no encontrada.");
                return;
            }

            System.out.print("Monto a transferir: ");
            BigDecimal monto = new BigDecimal(scanner.nextLine());

            cuentaService.transferir(getCuentaUsuario(), destino, monto);
            System.out.println("Transferencia exitosa.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verMovimientos() {
        CuentaBancaria c = getCuentaUsuario();
        System.out.println("=== MOVIMIENTOS ===");

        List<Movimiento> movs = movimientoRepo.obtenerMovimientosDeCuenta(c.getNumeroCuenta());

        movs.forEach(m -> System.out.println(
                m.getTipo() + " | " +
                        m.getFecha() + " | " +
                        m.getCantidad()
        ));
    }
}
