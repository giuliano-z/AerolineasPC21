package com.aerolineaspc21.modelo;

public class Reserva {
    private String id;
    private String pasajero;
    private String vuelo;

    public Reserva(String id, String pasajero, String vuelo) {
        this.id = id;
        this.pasajero = pasajero;
        this.vuelo = vuelo;
    }

    public String getId() {
        return id;
    }

    public String getPasajero() {
        return pasajero;
    }

    public String getVuelo() {
        return vuelo;
    }
}
