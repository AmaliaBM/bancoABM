package com.web.maven.bancoABMModel.model;
public class Usuario extends Persona {

    private String idUsuario;
    private String hashContrasenya;
    private String cuentaBancaria; // podría ser número de cuenta

    public Usuario() {
        super();
    }

    public Usuario(String idUsuario, String hashContrasenya, String cuentaBancaria) {
        this.idUsuario = idUsuario;
        this.hashContrasenya = hashContrasenya;
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getHashContrasenya() {
        return hashContrasenya;
    }

    public void setHashContrasenya(String hashContrasenya) {
        this.hashContrasenya = hashContrasenya;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}
