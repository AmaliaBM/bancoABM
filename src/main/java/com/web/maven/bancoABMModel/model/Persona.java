package com.web.maven.bancoABMModel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Persona {

    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private boolean estadoBloqueado;
    private LocalDateTime fechaAlta;

    // Constructor vacío
    public Persona() {
    }

    // Constructor completo
    public Persona(String nombre, String apellido, String dni,
                   LocalDate fechaNacimiento, boolean estadoBloqueado,
                   LocalDateTime fechaAlta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoBloqueado = estadoBloqueado;
        this.fechaAlta = fechaAlta;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isEstadoBloqueado() {
        return estadoBloqueado;
    }

    public void setEstadoBloqueado(boolean estadoBloqueado) {
        this.estadoBloqueado = estadoBloqueado;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    // Métodos de negocio del UML

    public void actualizarDatosPersona(String nuevoNombre, String nuevoApellido) {
        this.nombre = nuevoNombre;
        this.apellido = nuevoApellido;
    }

    public void bloquearPersona() {
        this.estadoBloqueado = true;
    }

    public String mostrarDatos() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", estadoBloqueado=" + estadoBloqueado +
                ", fechaAlta=" + fechaAlta +
                '}';
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}

