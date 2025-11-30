package com.aerolineaspc21.modelo;

public class Arista {
    private Vertice destino;
    private double tiempo;
    private double precioBase;

    public Arista(Vertice destino, double tiempo, double precioBase) {
        this.destino = destino;
        this.tiempo = tiempo;
        this.precioBase = precioBase;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getTiempo() {
        return tiempo;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}
