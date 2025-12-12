package com.web.maven.bancoABMModel.util;

import com.web.maven.bancoABMModel.model.CuentaBancaria;
import com.web.maven.bancoABMModel.model.Persona;
import com.web.maven.bancoABMModel.model.Usuario;

import java.util.List;

public class ContenedorJson {

    private List<Usuario> usuarios;
    private List<Persona> personas;
    private List<CuentaBancaria> cuentas;

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Persona> getPersonas() { return personas; }
    public List<CuentaBancaria> getCuentas() { return cuentas; }

    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
    public void setPersonas(List<Persona> personas) { this.personas = personas; }
    public void setCuentas(List<CuentaBancaria> cuentas) { this.cuentas = cuentas; }
}
