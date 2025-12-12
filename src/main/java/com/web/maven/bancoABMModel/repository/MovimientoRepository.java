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

    public MovimientoRepository(CuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
        cargarDesdeJson("data/movimientos.json");
    }

    private void cargarDesdeJson(String rutaRelativa) {
        try (Reader r = Thread.currentThread().getContextClassLoader().getResourceAsStream(rutaRelativa) != null ?
                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(rutaRelativa)) : null) {

            if (r == null) return;

            JsonElement root = JsonParser.parseReader(r);
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
                        // si tienes una clase concreta de Deposito (subclase), aqui crearla
                        // asumo clase concreta: DepositoSimple (si no, usa Retiro/Transferencia)
                        // ejemplo usando Deposito (si es abstracto, necesitas una implementación)
                        // omitimos depósitos si no hay implementación concreta
                        break;
                    default:
                        Retiro rmov = new Retiro(fecha, monto, origen, canal);
                        movimientos.add(rmov);
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
    }

    public List<Movimiento> obtenerMovimientosDeCuenta(String numeroCuenta) {
    }
}
