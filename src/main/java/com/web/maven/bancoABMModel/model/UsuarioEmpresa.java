package com.web.maven.bancoABMModel.model;

public class UsuarioEmpresa extends Usuario {

    private String razonSocial;
    private String cif;
    private String sector;

    public UsuarioEmpresa() {
        super();
    }

    public UsuarioEmpresa(String razonSocial, String cif, String sector) {
        this.razonSocial = razonSocial;
        this.cif = cif;
        this.sector = sector;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
