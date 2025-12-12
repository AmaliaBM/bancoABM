package com.web.maven.bancoABMModel.repository;

import com.google.gson.*;
import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.movimientos.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class MovimientoRepository {

    private final List<Movimiento> movimientos = new ArrayList<>();
    private final CuentaRepository cuentaRepo;

    // Constructor recibe el repo de cuentas
    public MovimientoRepository(CuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
        cargarDesdeJson("data/movimientos.json");
    }

    private void cargarDesdeJson(String rutaRelativa) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(rutaRelativa)) {

            if (is == null) return;

            Reader reader = new InputStreamReader(is);
            JsonElement root = JsonParser.parseReader(reader);

            if (!root.isJsonArray()) return;

            JsonArray arr = root.getAsJsonArray();

            for (JsonElement el : arr) {
                JsonObject obj = el.getAsJsonObject();
                String tipo = obj.has("tipo") ? obj.get("tipo").getAsString() : "Retiro";

                String origenNum = obj.has("cuentaOrigen") ? obj.get("cuentaOrigen").getAsString() : null;
                CuentaBancaria origen = origenNum != null ? cuentaRepo.buscarPorNumero(origenNum) : null;

                BigDecimal monto = obj.has("monto") ? new BigDecimal(obj.get("monto").getAsString()) : BigDecimal.ZERO;
                LocalDateTime fecha = obj.has("fecha") ? LocalDateTime.parse(obj.get("fecha").getAsString()) : LocalDateTime.now();
                String canal = obj.has("canal") ? obj.get("canal").getAsString() : "DESCONOCIDO";

                switch (tipo) {
                    case "Transferencia":
                        String destinoNum = obj.has("cuentaDestino") ? obj.get("cuentaDestino").getAsString() : null;
                        CuentaBancaria destino = destinoNum != null ? cuentaRepo.buscarPorNumero(destinoNum) : null;
                        Transferencia t = new Transferencia(fecha, monto, origen, canal, destino);
                        movimientos.add(t);
                        break;
                    case "Deposito":
                        movimientos.add(new DepositoBancario(fecha,monto,origen,canal));
                        break;
                    default:
                        Retiro r = new Retiro(fecha, monto, origen, canal);
                        movimientos.add(r);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Movimiento> cargarMovimientos() {
        return movimientos;
    }

    public void registrarMovimiento(Movimiento m) {
        movimientos.add(m);
        if (m.getCuentaOrigen() != null) {
            m.getCuentaOrigen().agregarMovimiento(m);
        }
        if (m instanceof Transferencia t && t.getCuentaDestino() != null) {
            t.getCuentaDestino().agregarMovimiento(m);
        }
    }

    public List<Movimiento> obtenerMovimientosDeCuenta(String numeroCuenta) {
        List<Movimiento> resultado = new ArrayList<>();
        for (Movimiento m : movimientos) {
            if (m.getCuentaOrigen() != null && numeroCuenta.equals(m.getCuentaOrigen().getNumeroCuenta())) {
                resultado.add(m);
            } else if (m instanceof Transferencia t && t.getCuentaDestino() != null &&
                    numeroCuenta.equals(t.getCuentaDestino().getNumeroCuenta())) {
                resultado.add(m);
            }
        }
        return resultado;
    }
}