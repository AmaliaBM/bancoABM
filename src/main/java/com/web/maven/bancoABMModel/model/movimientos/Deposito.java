package com.web.maven.bancoABMModel.model.movimientos;

public abstract class Deposito extends Movimiento {

    private String origen;

    public Deposito() {
        super();
    }

    public Deposito(String origen) {
        this.origen = origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
