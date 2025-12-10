package com.web.maven.bancoABMModel.model;

public class UsuarioParticular extends Usuario {

    private String estadoCivil;
    private String ocupacion;

    public UsuarioParticular() {
        super();
    }

    public UsuarioParticular(String estadoCivil, String ocupacion) {
        this.estadoCivil = estadoCivil;
        this.ocupacion = ocupacion;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}
