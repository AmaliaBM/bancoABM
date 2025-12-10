package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.Usuario;
import com.web.maven.bancoABMModel.util.JsonReader;

import java.math.BigDecimal;
import java.util.*;

public class CuentaRepository {

    private final Map<String, CuentaBancaria> cuentasByNumero = new HashMap<>();

    // Constructor que lee data/cuentas.json si existe
    public CuentaRepository() {
        List<CuentaBancaria> lista = JsonReader.cargarLista("data/cuentas.json", CuentaBancaria[].class);
        for (CuentaBancaria c : lista) {
            cuentasByNumero.put(c.getNumeroCuenta(), c);
        }
    }

    // alternativa: inicializar a partir de usuarios (útil si solo tienes usuarios.json)
    public void inicializarDesdeUsuarios(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            String num = u.getCuentaBancaria();
            if (num == null) continue;
            cuentasByNumero.computeIfAbsent(num, k -> new CuentaBancaria(k, BigDecimal.ZERO));
        }
    }

    public CuentaBancaria buscarPorNumero(String numero) {
        return cuentasByNumero.get(numero);
    }

    public Collection<CuentaBancaria> cargarCuentas() {
        return cuentasByNumero.values();
    }

    public void guardar(CuentaBancaria c) {
        cuentasByNumero.put(c.getNumeroCuenta(), c);
        // opcional: persistir al JSON
    }
}
