package com.aerolineaspc21.modelo;

import java.util.ArrayList;
import java.util.List;

public class AVL {
    public Reserva buscarPorAsiento(String asiento) {
        return buscarPorAsiento(raiz, asiento);
    }
    private Reserva buscarPorAsiento(NodoAVL nodo, String asiento) {
        if (nodo == null) return null;
        if (nodo.reserva.getId().equals(asiento)) return nodo.reserva;
        Reserva izq = buscarPorAsiento(nodo.izquierdo, asiento);
        if (izq != null) return izq;
        return buscarPorAsiento(nodo.derecho, asiento);
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }
    private int contarNodos(NodoAVL nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }
    private NodoAVL raiz;

    public void insertar(Reserva reserva) {
        raiz = insertar(raiz, reserva);
    }

    private NodoAVL insertar(NodoAVL nodo, Reserva reserva) {
        if (nodo == null) return new NodoAVL(reserva);
        int cmp = reserva.getId().compareTo(nodo.reserva.getId());
        if (cmp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, reserva);
        } else if (cmp > 0) {
            nodo.derecho = insertar(nodo.derecho, reserva);
        } else {
            return nodo;
        }
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        return balancear(nodo);
    }

    public Reserva buscar(String id) {
        NodoAVL nodo = buscar(raiz, id);
        return nodo != null ? nodo.reserva : null;
    }

    private NodoAVL buscar(NodoAVL nodo, String id) {
        if (nodo == null) return null;
        int cmp = id.compareTo(nodo.reserva.getId());
        if (cmp < 0) return buscar(nodo.izquierdo, id);
        if (cmp > 0) return buscar(nodo.derecho, id);
        return nodo;
    }

    public void eliminar(String id) {
        raiz = eliminar(raiz, id);
    }

    private NodoAVL eliminar(NodoAVL nodo, String id) {
        if (nodo == null) return null;
        int cmp = id.compareTo(nodo.reserva.getId());
        if (cmp < 0) {
            nodo.izquierdo = eliminar(nodo.izquierdo, id);
        } else if (cmp > 0) {
            nodo.derecho = eliminar(nodo.derecho, id);
        } else {
            if (nodo.izquierdo == null || nodo.derecho == null) {
                nodo = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;
            } else {
                NodoAVL sucesor = minValorNodo(nodo.derecho);
                nodo.reserva = sucesor.reserva;
                nodo.derecho = eliminar(nodo.derecho, sucesor.reserva.getId());
            }
        }
        if (nodo == null) return null;
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        return balancear(nodo);
    }

    private NodoAVL minValorNodo(NodoAVL nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo;
    }

    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int balance(NodoAVL nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int balance = balance(nodo);
        if (balance > 1) {
            if (balance(nodo.izquierdo) < 0) {
                nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            }
            return rotacionDerecha(nodo);
        }
        if (balance < -1) {
            if (balance(nodo.derecho) > 0) {
                nodo.derecho = rotacionDerecha(nodo.derecho);
            }
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;
        x.derecho = y;
        y.izquierdo = T2;
        y.altura = 1 + Math.max(altura(y.izquierdo), altura(y.derecho));
        x.altura = 1 + Math.max(altura(x.izquierdo), altura(x.derecho));
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;
        y.izquierdo = x;
        x.derecho = T2;
        x.altura = 1 + Math.max(altura(x.izquierdo), altura(x.derecho));
        y.altura = 1 + Math.max(altura(y.izquierdo), altura(y.derecho));
        return y;
    }

    public List<Reserva> inOrder() {
        List<Reserva> resultado = new ArrayList<>();
        inOrder(raiz, resultado);
        return resultado;
    }

    private void inOrder(NodoAVL nodo, List<Reserva> resultado) {
        if (nodo != null) {
            inOrder(nodo.izquierdo, resultado);
            resultado.add(nodo.reserva);
            inOrder(nodo.derecho, resultado);
        }
    }
}
