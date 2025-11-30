package com.aerolineaspc21.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vertice implements Comparable<Vertice> {
    // ...existing code...
    public int compareTo(Vertice otro) {
        if (this.tiempoMinimoAcumulado < otro.tiempoMinimoAcumulado) return -1;
        if (this.tiempoMinimoAcumulado > otro.tiempoMinimoAcumulado) return 1;
        if (this.precioTotalAcumulado < otro.precioTotalAcumulado) return -1;
        if (this.precioTotalAcumulado > otro.precioTotalAcumulado) return 1;
        return 0;
    }
    private String nombre;
    private List<Arista> adyacentes;
    private double tiempoMinimoAcumulado;
    private double precioTotalAcumulado;
    private Vertice predecesor;
    private boolean visitado;
    public static final double INFINITO = Double.MAX_VALUE;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ArrayList<>();
        clear();
    }

    public void clear() {
        this.tiempoMinimoAcumulado = INFINITO;
        this.precioTotalAcumulado = 0.0;
        this.predecesor = null;
        this.visitado = false;
    }

    public String getNombre() {
        return nombre;
    }
    public List<Arista> getAdyacentes() {
        return adyacentes;
    }
    public void agregarArista(Arista arista) {
        adyacentes.add(arista);
    }
    public double getTiempoMinimoAcumulado() {
        return tiempoMinimoAcumulado;
    }
    public void setTiempoMinimoAcumulado(double t) {
        this.tiempoMinimoAcumulado = t;
    }
    public double getPrecioTotalAcumulado() {
        return precioTotalAcumulado;
    }
    public void setPrecioTotalAcumulado(double p) {
        this.precioTotalAcumulado = p;
    }
    public Vertice getPredecesor() {
        return predecesor;
    }
    public void setPredecesor(Vertice v) {
        this.predecesor = v;
    }
    public boolean isVisitado() {
        return visitado;
    }
    public void setVisitado(boolean v) {
        this.visitado = v;
    }
}
