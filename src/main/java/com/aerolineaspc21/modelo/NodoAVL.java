package com.aerolineaspc21.modelo;

public class NodoAVL {
    public Reserva reserva;
    public NodoAVL izquierdo;
    public NodoAVL derecho;
    public int altura;

    public NodoAVL(Reserva reserva) {
        this.reserva = reserva;
        this.altura = 1;
    }
}
