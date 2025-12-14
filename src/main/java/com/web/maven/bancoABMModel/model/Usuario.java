package com.web.maven.bancoABMModel.model;

public class Usuario {

    private int id; // <- campo para ID autoincrement
    private String idUsuario;
    private String hashContrasenya;
    private String cuentaBancaria;
    private String nombre;
    private String apellido;
    private String dni;
    private String tipo; // obligatorio

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getHashContrasenya() { return hashContrasenya; }
    public void setHashContrasenya(String hashContrasenya) { this.hashContrasenya = hashContrasenya; }

    public String getCuentaBancaria() { return cuentaBancaria; }
    public void setCuentaBancaria(String cuentaBancaria) { this.cuentaBancaria = cuentaBancaria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
