package com.web.maven.bancoABMModel;

import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.DepositoBancario;
import com.web.maven.bancoABMModel.model.movimientos.Retiro;
import com.web.maven.bancoABMModel.model.movimientos.Transferencia;

import com.web.maven.bancoABMModel.repository.UsuarioRepository;
import com.web.maven.bancoABMModel.repository.CuentaRepository;
import com.web.maven.bancoABMModel.repository.MovimientoRepository;
import com.web.maven.bancoABMModel.util.JsonLoader;

import java.math.BigDecimal;
import java.util.Scanner;

public class AppConsola {

    private static final Scanner sc = new Scanner(System.in);

    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final CuentaRepository cuentaRepo = new CuentaRepository();
    private static final MovimientoRepository movimientoRepo = new MovimientoRepository();

    public static void main(String[] args) {

        System.out.println("=== CARGANDO DATOS DESDE JSON ===");

        // ✔️ USO CORRECTO JsonLoader
        JsonLoader loader = new JsonLoader(usuarioRepo, cuentaRepo, movimientoRepo);
        loader.cargarTodo();

        int opcion;
        do {
            System.out.println("\n=== BANCO ABM ===");
            System.out.println("1. Login");
            System.out.println("2. Crear nuevo usuario");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> login();
                case 2 -> crearUsuario();
            }
        } while (opcion != 0);
    }

    private static void login() {
        System.out.print("DNI: ");
        String dni = sc.nextLine();

        Usuario u = usuarioRepo.buscarPorDni(dni);
        if (u == null) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }
        menuPrincipal(u);
    }

    private static void menuPrincipal(Usuario u) {

        int opcion;
        do {
            System.out.println("\n=== MENÚ ===");
            System.out.println("1. Ver saldo");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Transferir");
            System.out.println("5. Ver movimientos");
            System.out.println("0. Logout");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> verSaldo(u);
                case 2 -> depositar(u);
                case 3 -> retirar(u);
                case 4 -> transferir(u);
                case 5 -> verMovimientos(u); // <- llamar método nuevo
            }
        } while (opcion != 0);
    }

    private static void verSaldo(Usuario u) {
        CuentaBancaria c = cuentaRepo.obtenerCuenta(u.getCuentaBancaria());
        System.out.println("Saldo actual: " + c.getSaldo());
    }

    private static void depositar(Usuario u) {

        System.out.print("Cantidad a depositar: ");
        BigDecimal cantidad = sc.nextBigDecimal();

        CuentaBancaria c = cuentaRepo.obtenerCuenta(u.getCuentaBancaria());
        cuentaRepo.actualizarSaldo(c.getNumeroCuenta(), c.getSaldo().add(cantidad));

        DepositoBancario d = new DepositoBancario(cantidad, c);
        movimientoRepo.registrar(d);

        System.out.println("✔ Depósito realizado");
    }

    private static void retirar(Usuario u) {

        System.out.print("Cantidad a retirar: ");
        BigDecimal cantidad = sc.nextBigDecimal();

        CuentaBancaria c = cuentaRepo.obtenerCuenta(u.getCuentaBancaria());

        if (c.getSaldo().compareTo(cantidad) < 0) {
            System.out.println("❌ Saldo insuficiente");
            return;
        }

        cuentaRepo.actualizarSaldo(c.getNumeroCuenta(), c.getSaldo().subtract(cantidad));
        movimientoRepo.registrar(new Retiro(cantidad, c));

        System.out.println("✔ Retiro realizado");
    }

    private static void transferir(Usuario u) {

        System.out.print("Cuenta destino: ");
        String destinoNum = sc.nextLine();

        CuentaBancaria origen = cuentaRepo.obtenerCuenta(u.getCuentaBancaria());
        CuentaBancaria destino = cuentaRepo.obtenerCuenta(destinoNum);

        if (destino == null) {
            System.out.println("❌ Cuenta destino no existe");
            return;
        }

        System.out.print("Cantidad a transferir: ");
        BigDecimal cantidad = sc.nextBigDecimal();

        if (origen.getSaldo().compareTo(cantidad) < 0) {
            System.out.println("❌ Saldo insuficiente");
            return;
        }

        cuentaRepo.actualizarSaldo(origen.getNumeroCuenta(), origen.getSaldo().subtract(cantidad));
        cuentaRepo.actualizarSaldo(destino.getNumeroCuenta(), destino.getSaldo().add(cantidad));

        movimientoRepo.registrar(new Transferencia(cantidad, origen, destino));
        System.out.println("✔ Transferencia realizada");
    }

    private static void crearUsuario() {

        System.out.print("DNI: ");
        String dni = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        // 1️⃣ Generar número de cuenta
        String numeroCuenta = cuentaRepo.generarNumeroCuenta();

        // 2️⃣ Crear cuenta en BD (saldo 0)
        cuentaRepo.crearCuenta(numeroCuenta);

        // 3️⃣ Crear usuario (SIN constructor inexistente)
        Usuario u = new Usuario();
        u.setDni(dni);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setCuentaBancaria(numeroCuenta);
        u.setHashContrasenya(pass);
        u.setTipo("UsuarioParticular");

        // 4️⃣ Guardar usuario
        usuarioRepo.guardarEnBD(u);

        System.out.println("✔ Usuario creado con cuenta " + numeroCuenta);
    }

    private static void verMovimientos(Usuario u) {
        String numeroCuenta = u.getCuentaBancaria();
        var movimientos = movimientoRepo.obtenerPorCuenta(numeroCuenta); // <- método del repo

        if (movimientos.isEmpty()) {
            System.out.println("❌ No hay movimientos registrados");
            return;
        }

        System.out.println("\n=== MOVIMIENTOS DE LA CUENTA " + numeroCuenta + " ===");
        for (var m : movimientos) {
            System.out.println("Fecha: " + m.getFecha() +
                    " | Tipo: " + m.getTipo() +
                    " | Cantidad: " + m.getCantidad() +
                    (m.getCuentaDestino() != null ? " | Destino: " + m.getCuentaDestino().getNumeroCuenta() : ""));
        }
    }


}
